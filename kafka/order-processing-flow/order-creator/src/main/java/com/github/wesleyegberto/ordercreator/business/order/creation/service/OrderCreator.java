package com.github.wesleyegberto.ordercreator.business.order.creation.service;

import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wesleyegberto.ordercreator.business.order.creation.entity.CreatedOrder;
import com.github.wesleyegberto.ordercreator.business.order.creation.entity.Order;
import com.github.wesleyegberto.ordercreator.business.order.processing.producer.OrderProcessingProducer;

@Service
public class OrderCreator {
	private Random random = new Random();
	private OrderProcessingProducer processingSender;
	
	@Autowired
	public OrderCreator(OrderProcessingProducer processingSender) {
		this.processingSender = processingSender;
	}

	public CreatedOrder create(Order order) {
		OrderValidator.validate(order);
		processCreation(order);
		processingSender.sendOrderToProcessing(order);
		return CreatedOrder.from(order);
	}

	private void processCreation(Order order) {
		var id = UUID.randomUUID();
		var number = String.format("%02d-%04d", random.nextInt(100), random.nextInt(10000));
		order.setCreated(id, number);
	}
}
