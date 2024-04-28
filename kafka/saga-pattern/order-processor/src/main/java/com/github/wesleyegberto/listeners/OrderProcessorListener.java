package com.github.wesleyegberto.listeners;

import java.time.Duration;

import com.github.wesleyegberto.model.Order;
import com.github.wesleyegberto.service.OrderManageService;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessorListener {
	private static final Logger LOG = LoggerFactory.getLogger(OrderProcessorListener.class);

	private OrderManageService orderManageService;

	public OrderProcessorListener(OrderManageService manageService) {
		this.orderManageService = manageService;
	}

	@Bean
	public KStream<Long, Order> stream(StreamsBuilder builder) {
		var orderSerde = new JsonSerde<>(Order.class);

		var paymentStream = builder.stream("payment-orders", Consumed.with(Serdes.Long(), orderSerde));

		paymentStream.join(
						builder.stream("stock-orders"),
						orderManageService::confirm,
						JoinWindows.of(Duration.ofSeconds(10)),
						StreamJoined.with(Serdes.Long(), orderSerde, orderSerde)
				)
				.peek((k, o) -> LOG.info("Output: {}", o))
				.to("orders");

		return paymentStream;
	}

	@Bean
	public KTable<Long, Order> table(StreamsBuilder builder) {
		KeyValueBytesStoreSupplier store = Stores.persistentKeyValueStore("orders");
		var orderSerde = new JsonSerde<>(Order.class);
		var stream = builder.stream("orders", Consumed.with(Serdes.Long(), orderSerde));
		return stream.toTable(Materialized.<Long, Order>as(store)
				.withKeySerde(Serdes.Long())
				.withValueSerde(orderSerde));
	}
}
