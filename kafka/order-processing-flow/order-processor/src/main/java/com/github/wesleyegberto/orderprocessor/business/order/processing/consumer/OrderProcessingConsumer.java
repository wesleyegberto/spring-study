package com.github.wesleyegberto.orderprocessor.business.order.processing.consumer;

import java.io.IOException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.orderprocessor.business.order.processing.entity.Order;
import com.github.wesleyegberto.orderprocessor.business.order.processing.service.OrderProcessor;

@Component
public class OrderProcessingConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(OrderProcessingConsumer.class);
	
	private ObjectMapper objectMapper;
	private OrderProcessor orderProcessor;

	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public OrderProcessingConsumer(ObjectMapper objectMapper, OrderProcessor orderProcessor, KafkaTemplate<String, String> kafkaTemplate) {
		this.objectMapper = objectMapper;
		this.orderProcessor = orderProcessor;
		this.kafkaTemplate = kafkaTemplate;
	}

	@KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.order-processing.topic.name}")
	public void consume(Message<String> kafkaMessage, Acknowledgment ack) throws IOException {
		LOG.info("== Message Erro Channel: {}", new String((byte[]) kafkaMessage.getHeaders().getErrorChannel()));
		
		var message = kafkaMessage.getPayload();
		LOG.info("Message received: " + message);
		var order = objectMapper.readValue(message, Order.class);
		try {
			this.orderProcessor.process(order);
			ack.acknowledge();
		} catch (Exception ex) {
			LOG.error("Error during PROCESSING of order {}: {}", order.getId(), ex.getMessage());
			this.sendToErrorChannel(kafkaMessage);
			throw ex;
		}
	}

	private void sendToErrorChannel(Message<String> kafkaMessage) {
		var errorChannel = new String((byte[]) kafkaMessage.getHeaders().getErrorChannel());
		var errorMessage = new ProducerRecord<String, String>(errorChannel, kafkaMessage.getPayload());
		kafkaTemplate.send(errorMessage);
	}
}
