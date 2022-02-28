package com.github.wesleyegberto.consumer.service;

import com.github.wesleyegberto.consumer.model.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(EventConsumer.class);

	public void logBuyOrder(Order order) {
		LOG.info("Received BUY order: {}", order);
	}

	public void logSellOrder(Order order) {
		LOG.info("Received SELL order: {}", order);
	}
}
