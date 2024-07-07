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
	private final String ordersValidatedTopicName;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public OrderEventPublisher(@Value("${kafka.orders-placed.topic.name}") String ordersPlacedTopicName,
			@Value("${kafka.orders-validated.topic.name}") String ordersValidatedTopicName,
			KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.ordersPlacedTopicName = ordersPlacedTopicName;
		this.ordersValidatedTopicName = ordersValidatedTopicName;
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	public void notifyPlacedOrder(OrderPlacedEvent event) {
		var message = createMessage(this.ordersPlacedTopicName, event.orderNumber(), event);
		LOG.info("Notifying placed order {} - {}: {}", event.id(), event.orderNumber(), message);
		this.kafkaTemplate.send(message);
	}

	public void notifyValidatedOrder(OrderValidatedEvent event) {
		var message = createMessage(this.ordersValidatedTopicName, event.orderNumber(), event);
		LOG.info("Notifying validated order {} - {}: {}", event.id(), event.orderNumber(), message);
		this.kafkaTemplate.send(message);
	}

	private <T> ProducerRecord<String, String> createMessage(String topicName, String id, T event) {
		var messageContent = serialize(event);
		return new ProducerRecord<>(topicName, id, messageContent);
	}

	private <T> String serialize(T event) {
		try {
			return objectMapper.writeValueAsString(event);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}

}
