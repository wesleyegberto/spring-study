package com.github.wesleyegberto.consumer;

import java.time.Duration;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import com.github.wesleyegberto.consumer.model.Order;
import com.github.wesleyegberto.consumer.model.Transaction;
import com.github.wesleyegberto.consumer.model.TransactionTotal;
import com.github.wesleyegberto.consumer.service.EventConsumer;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

@SpringBootApplication
public class KakfaConsumerApplication {
	private static final Logger LOG = LoggerFactory.getLogger(KakfaConsumerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KakfaConsumerApplication.class, args);
	}

	@Bean
	public BiConsumer<KStream<Long, Order>, KStream<Long, Order>> combineOrders() {
		return (buyOrder, sellOrder) -> {
			buyOrder.merge(sellOrder)
				.peek((k, v) -> {
					LOG.info("Merging orders: ID {} - {}", k, v);
				});
		};
	}

	@Bean
	public BiFunction<KStream<Long, Order>, KStream<Long, Order>, KStream<Long, Transaction>> joinOrders(EventConsumer eventConsumer) {
		return (buyOrder, sellOrder) -> {
			return buyOrder.selectKey((k, v) -> v.getProduct())
				.join(
					sellOrder.selectKey((k, v) -> v.getProduct()),
					eventConsumer::tryMakeTransaction,
					JoinWindows.of(Duration.ofSeconds(10)),
					StreamJoined.with(Serdes.String(), new JsonSerde<>(Order.class), new JsonSerde<>(Order.class))
				)
				.filterNot((k, v) -> v == null)
				.map((k, v) -> KeyValue.pair(v.getId(), v))
				.peek((k, v) -> {
					LOG.info("Transactioned: ID {} - {}", k, v);
				});
		};
	}

	@Bean
	public Consumer<KStream<Long, Transaction>> totalizer() {
		var storeSuplier = Stores.persistentKeyValueStore("all-transactions-store");

		return transactions -> {
			transactions.groupBy((k, v) -> v.getProduct(), Grouped.with(Serdes.String(), new JsonSerde<>(Transaction.class)))
				.aggregate(
					TransactionTotal::new,
					(k, v, acc) -> {
						acc.registerAmount(v.getAmount());
						return acc;
					},
					Materialized.<String, TransactionTotal>as(storeSuplier)
						.withKeySerde(Serdes.String())
						.withValueSerde(new JsonSerde<>(TransactionTotal.class))
				)
				.toStream()
				.peek((k, v) -> LOG.info("Total: {}", v));
		};
	}
}

