package com.github.wesleyegberto.controller;

import java.util.concurrent.atomic.AtomicInteger;

import com.github.wesleyegberto.model.Order;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrdersController {
	private static final AtomicInteger ID = new AtomicInteger();

	private final KafkaTemplate<Integer, Order> kafkaTemplate;

	public OrdersController(KafkaTemplate<Integer, Order> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	@PostMapping
	public ResponseEntity<Order> create(@RequestBody Order order) {
		order.setId(ID.incrementAndGet());
		this.kafkaTemplate.send("orders", order);
		return ResponseEntity.ok(order);
	}
}
