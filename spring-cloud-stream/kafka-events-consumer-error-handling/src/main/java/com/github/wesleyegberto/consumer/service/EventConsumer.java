package com.github.wesleyegberto.consumer.service;

import com.github.wesleyegberto.consumer.model.BuyOrder;
import com.github.wesleyegberto.consumer.model.SellOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(EventConsumer.class);

	public void processBuy(BuyOrder order) {
		LOG.info("Received BUY order: {}", order);
		throw new RuntimeException("Error during buy - no money");
	}

	public void processSell(SellOrder order) {
		LOG.info("Received SELL order: {}", order);
	}
}
