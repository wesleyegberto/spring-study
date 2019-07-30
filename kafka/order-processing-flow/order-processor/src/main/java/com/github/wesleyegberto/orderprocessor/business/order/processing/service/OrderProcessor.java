package com.github.wesleyegberto.orderprocessor.business.order.processing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wesleyegberto.orderprocessor.business.order.invoicing.producer.OrderInvoicingProducer;
import com.github.wesleyegberto.orderprocessor.business.order.processing.entity.Order;

@Service
public class OrderProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(OrderProcessor.class);
	
	private OrderInvoicingProducer orderInvoicingSender;

	@Autowired
	public OrderProcessor(OrderInvoicingProducer orderInvoicingSender) {
		this.orderInvoicingSender = orderInvoicingSender;
	}
	
	public void process(Order order) {
		LOG.info("Processing order {}", order.getId());
		
		LOG.info("Validating stock quantity...");
		if (Math.random() < 0.01) {
			LOG.warn("Out of stock!");
			throw new IllegalStateException("Out of stock for order...");
		}
		orderInvoicingSender.sendOrderToInvoicing(order);
	}
}
