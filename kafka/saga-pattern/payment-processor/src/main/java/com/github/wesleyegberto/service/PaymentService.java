package com.github.wesleyegberto.service;

import com.github.wesleyegberto.model.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
	private static final Logger LOG = LoggerFactory.getLogger(PaymentService.class);
	private static final String PAYMENT_SOURCE = "PAYMENT";

	private final KafkaTemplate<Integer, Order> kafkaTemplate;

	public PaymentService(KafkaTemplate<Integer, Order> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void invoice(Order order) {
		if (order.getPrice() < 100) {
			LOG.info("Reserving amount");
			order.setStatus("ACCEPTED");
		} else {
			order.setStatus("REJECTED");
		}
		order.setSource(PAYMENT_SOURCE);
		kafkaTemplate.send("payment-orders", order.getId(), order);
		LOG.info("Sent: {}", order);
	}

	public void confirm(Order order) {
		LOG.info("Result of order confirmation: ", order.getStatus());

		if (order.getStatus().equals("CONFIRMED")) {
			LOG.info("Confirming amount");

		} else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(PAYMENT_SOURCE)) {
			LOG.info("Refunding amount");
		}
	}
}
