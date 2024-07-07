package com.wesleyegberto.ecommerce.orders.validator;

import java.time.Duration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesleyegberto.ecommerce.events.OrderPlacedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedKafkaListener {
	private static final Logger LOG = LoggerFactory.getLogger(OrderPlacedKafkaListener.class);

	private final ObjectMapper objectMapper;
	private final OrderValidatorService processingService;

	public OrderPlacedKafkaListener(ObjectMapper objectMapper, OrderValidatorService processingService) {
		this.objectMapper = objectMapper;
		this.processingService = processingService;
	}

	@KafkaListener(
			topics = "${kafka.orders-placed.topic.name}",
			containerFactory = "orderProcessorListenerContainerFactory"
	)
	public void onMessage(Message<String> kafkaMessage, Acknowledgment ack) {
		var message = kafkaMessage.getPayload();
		LOG.info("Message received: {}", message);

		try {
			var event = objectMapper.readValue(message, OrderPlacedEvent.class);
			processingService.processOrderPlacedEvent(event);
			ack.acknowledge();
		} catch (JsonProcessingException e) {
			LOG.error("Error at deserialization: {}", e.getMessage());
			ack.nack(Duration.ofSeconds(1));
		}
	}
}
