package com.github.wesleyegberto.orderprocessor.business.order.invoicing.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.orderprocessor.business.order.processing.entity.Order;

@Service
public class OrderInvoicingProducer {
	private static final Logger LOG = LoggerFactory.getLogger(OrderInvoicingProducer.class);
	
    private String orderInvoicingTopicName;
    
    private ObjectMapper objectMapper;
    private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public OrderInvoicingProducer(@Value("${kafka.order-invoicing.topic.name}") String topicName,
			ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
		this.orderInvoicingTopicName = topicName;
		this.objectMapper = objectMapper;
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendOrderToInvoicing(Order order) {
		LOG.info("Sending order {} to INVOICE", order.getId());
		String messageContent;
		try {
			messageContent = objectMapper.writeValueAsString(order);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		kafkaTemplate.send(orderInvoicingTopicName, messageContent);
	}
}
