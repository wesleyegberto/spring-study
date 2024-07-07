package com.wesleyegberto.ecommerce.orders.processing;

import com.wesleyegberto.ecommerce.events.OrderValidatedEvent;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
public class OrderAuditorStreamProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(OrderAuditorStreamProcessor.class);

	@Value("${kafka.orders-validated.topic.name}")
	private String ordersValidatedTopicName;

	private final OrderProcessorService orderProcessorService;

	public OrderAuditorStreamProcessor(OrderProcessorService orderProcessorService) {
		this.orderProcessorService = orderProcessorService;
	}

	@Autowired
	public void orderAuditorTopology(StreamsBuilder streamsBuilder) {
		final var stringSerde = Serdes.String();
		final var typedSerde = new JsonSerde<>(OrderValidatedEvent.class);

		KStream<String, OrderValidatedEvent> ordersValidatedStream = streamsBuilder.stream(ordersValidatedTopicName,
				Consumed.with(stringSerde, typedSerde));

		ordersValidatedStream
				.peek((key, value) -> LOG.info("Record received {} - {}", key, value))
				.foreach((key, value) -> {
					try {
						orderProcessorService.processOrderValidatedEvent(value);
					} catch (Exception ex) {
						LOG.error("Error during record processing: {}", ex.getMessage());
					}
				});
	}
}
