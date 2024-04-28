package com.github.wesleyegberto.listeners;

import com.github.wesleyegberto.model.Order;
import com.github.wesleyegberto.service.StockService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class StockProcessorListener {
	private static final Logger LOG = LoggerFactory.getLogger(StockProcessorListener.class);

	private final StockService orderManageService;

	public StockProcessorListener(StockService manageService) {
		this.orderManageService = manageService;
	}

	@KafkaListener(id = "orders", topics = "orders", groupId = "stock")
	public void onEvent(Order order) {
		LOG.info("Received: {}", order);

		if (order.getStatus().equals("NEW"))
			orderManageService.reserve(order);
		else
			orderManageService.confirm(order);
	}
}
