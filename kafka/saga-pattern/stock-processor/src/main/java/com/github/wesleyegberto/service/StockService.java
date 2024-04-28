package com.github.wesleyegberto.service;

import com.github.wesleyegberto.model.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StockService {
	private static final Logger LOG = LoggerFactory.getLogger(StockService.class);
	private static final String STOCK_SOURCE = "STOCK";

	private final KafkaTemplate<Integer, Order> kafkaTemplate;

	public StockService(KafkaTemplate<Integer, Order> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void reserve(Order order) {
		if (order.getProductId() == 3) {
			order.setStatus("REJECTED");
		} else {
			LOG.info("Reserving stock");
			order.setStatus("ACCEPTED");
		}
		order.setSource(STOCK_SOURCE);
		kafkaTemplate.send("stock-orders", order.getId(), order);
		LOG.info("Sent: {}", order);
	}

	public void confirm(Order order) {
		LOG.info("Result of order confirmation: ", order.getStatus());

		if (order.getStatus().equals("CONFIRMED")) {
			LOG.info("Decreasing stock");

		} else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(STOCK_SOURCE)) {
			LOG.info("Refunding stock");
		}
	}
}
