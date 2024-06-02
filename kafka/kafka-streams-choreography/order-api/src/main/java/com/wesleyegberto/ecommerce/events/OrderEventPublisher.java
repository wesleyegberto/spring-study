package com.wesleyegberto.ecommerce.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderEventPublisher {
	private static final Logger LOG = LoggerFactory.getLogger(OrderEventPublisher.class);

	private final String ordersPlacedTopicName;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public OrderEventPublisher(@Value("${kafka.orders-placed.topic.name}") String ordersPlacedTopicName,
			KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.ordersPlacedTopicName = ordersPlacedTopicName;
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	public void placeOrder(OrderPlacedEvent event) {
		var message = createMessage(event);
		LOG.info("Placing order {} - {}: {}", event.id(), event.orderNumber(), message);
		this.kafkaTemplate.send(message);
	}

	private ProducerRecord<String, String> createMessage(OrderPlacedEvent event) {
		var messageContent = serialize(event);
		return new ProducerRecord<>(this.ordersPlacedTopicName,
				event.orderNumber(), messageContent);
	}

	private String serialize(OrderPlacedEvent event) {
		try {
			return objectMapper.writeValueAsString(event);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}

}
