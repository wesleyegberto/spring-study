package com.github.wesleyegberto.orderinvoicer.business.order.invoicing.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wesleyegberto.orderinvoicer.business.order.invoicing.entity.Order;
import com.github.wesleyegberto.orderinvoicer.business.order.shipping.producer.OrderShippingProducer;

@Service
public class OrderInvoicer {
	private static final Logger LOG = LoggerFactory.getLogger(OrderInvoicer.class);
	
	private OrderShippingProducer orderInvoicingSender;

	@Autowired
	public OrderInvoicer(OrderShippingProducer orderInvoicingSender) {
		this.orderInvoicingSender = orderInvoicingSender;
	}
	
	public void invoice(Order order) {
		LOG.info("Invoicing order {}", order.getId());
		
		LOG.info("Credit card invoiced...");
		
		orderInvoicingSender.sendOrderToShipping(order);
	}
}
