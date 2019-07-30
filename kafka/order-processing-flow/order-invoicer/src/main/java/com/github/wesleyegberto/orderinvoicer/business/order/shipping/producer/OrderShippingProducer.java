package com.github.wesleyegberto.orderinvoicer.business.order.shipping.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.orderinvoicer.business.order.invoicing.entity.Order;

@Service
public class OrderShippingProducer {
	private static final Logger LOG = LoggerFactory.getLogger(OrderShippingProducer.class);
	
    private String orderInvoicingTopicName;
    
	private ObjectMapper objectMapper;
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	public OrderShippingProducer(@Value("${kafka.order-shipping.topic.name}") String topicName,
			ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
		this.orderInvoicingTopicName = topicName;
		this.objectMapper = objectMapper;
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendOrderToShipping(Order order) {
		LOG.info("Sending order {} to SHIPPING", order.getId());
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
