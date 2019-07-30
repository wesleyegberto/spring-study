package com.github.wesleyegberto.ordercreator.business.order.processing.producer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.ordercreator.business.order.creation.entity.Order;

@Service
public class OrderProcessingProducer {
	private static final Logger LOG = LoggerFactory.getLogger(OrderProcessingProducer.class);
	
	private ObjectMapper objectMapper;
	private KafkaTemplate<String, String> kafkaTemplate;
	private String orderProcessingTopicName;

	@Autowired
	public OrderProcessingProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper,
			@Value("${kafka.order-processing.topic.name}") String topicName) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
		this.orderProcessingTopicName = topicName;
	}
	
	public void sendOrderToProcessing(Order order) {
		LOG.info("Sending order {} to PROCESS", order.getId());
		
		// kafkaTemplate.send(orderProcessingTopicName, order);
		String messageContent;
		try {
			messageContent = objectMapper.writeValueAsString(order);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		
		var message = new ProducerRecord<String, String>(orderProcessingTopicName, messageContent);
		message.headers()
			.add(MessageHeaders.ERROR_CHANNEL, "order-processing-errors".getBytes());
		
		// kafkaTemplate.send(orderProcessingTopicName, messageContent);
		kafkaTemplate.send(message);
	}
}
	