package com.github.wesleyegberto.orderinvoicer.business.order.invoicing.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.orderinvoicer.business.order.invoicing.entity.Order;
import com.github.wesleyegberto.orderinvoicer.business.order.invoicing.service.OrderInvoicer;

@Component
public class OrderInvoicingConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(OrderInvoicingConsumer.class);
	
	private ObjectMapper objectMapper;
	private OrderInvoicer orderInvoicer;

	@Autowired
	public OrderInvoicingConsumer(ObjectMapper objectMapper, OrderInvoicer orderInvoicer) {
		this.objectMapper = objectMapper;
		this.orderInvoicer = orderInvoicer;
	}

	@KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.order-invoicing.topic.name}")
	public void consume(String message, Acknowledgment ack) throws IOException {
		LOG.info("Message received: " + message);
		var order = objectMapper.readValue(message, Order.class);
		try {
			this.orderInvoicer.invoice(order);
			ack.acknowledge();
		} catch (Exception ex) {
			LOG.error("Error during INVOICING of order {}: {}", order.getId(), ex.getMessage());
			throw ex;
		}
	}
}
