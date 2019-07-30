package com.github.wesleyegberto.ordershipment.business.order.shipping.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.wesleyegberto.ordershipment.business.order.shipping.entity.Order;

@Service
public class OrderShipping {
	private static final Logger LOG = LoggerFactory.getLogger(OrderShipping.class);
	
	public void ship(Order order) {
		LOG.info("Shipping order {}", order.getId());
	}
}
