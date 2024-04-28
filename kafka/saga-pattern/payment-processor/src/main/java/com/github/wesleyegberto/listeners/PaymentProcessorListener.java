package com.github.wesleyegberto.listeners;

import com.github.wesleyegberto.model.Order;
import com.github.wesleyegberto.service.PaymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessorListener {
	private static final Logger LOG = LoggerFactory.getLogger(PaymentProcessorListener.class);

	private final PaymentService orderManageService;

	public PaymentProcessorListener(PaymentService manageService) {
		this.orderManageService = manageService;
	}

	@KafkaListener(id = "orders", topics = "orders", groupId = "payment")
	public void onEvent(Order order) {
		LOG.info("Received: {}", order);

		if (order.getStatus().equals("NEW"))
			orderManageService.invoice(order);
		else
			orderManageService.confirm(order);
	}
}
