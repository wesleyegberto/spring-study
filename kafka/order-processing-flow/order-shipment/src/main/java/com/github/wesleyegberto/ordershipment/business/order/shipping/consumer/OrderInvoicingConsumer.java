package com.github.wesleyegberto.ordershipment.business.order.shipping.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.ordershipment.business.order.shipping.entity.Order;
import com.github.wesleyegberto.ordershipment.business.order.shipping.service.OrderShipping;

@Component
public class OrderInvoicingConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(OrderInvoicingConsumer.class);
	
	private ObjectMapper objectMapper;
	private OrderShipping orderShipping;

	@Autowired
	public OrderInvoicingConsumer(ObjectMapper objectMapper, OrderShipping orderShipping) {
		this.objectMapper = objectMapper;
		this.orderShipping = orderShipping;
	}

	@KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.order-shipping.topic.name}")
	public void consume(String message, Acknowledgment ack) throws IOException {
		LOG.info("Message received: " + message);
		var order = objectMapper.readValue(message, Order.class);
		try {
			this.orderShipping.ship(order);
			ack.acknowledge();
		} catch (Exception ex) {
			LOG.error("Error during SHIPPING of order {}: {}", order.getId(), ex.getMessage());
			throw ex;
		}
	}
}
