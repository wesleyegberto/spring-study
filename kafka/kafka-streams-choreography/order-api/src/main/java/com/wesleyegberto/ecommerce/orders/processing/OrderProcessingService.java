package com.wesleyegberto.ecommerce.orders.processing;

import com.wesleyegberto.ecommerce.events.OrderPlacedEvent;
import com.wesleyegberto.ecommerce.orders.management.Order;
import com.wesleyegberto.ecommerce.orders.management.OrderRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessingService {
	private static final Logger LOG = LoggerFactory.getLogger(OrderProcessingService.class);

	private final OrderRepository orders;

	public OrderProcessingService(OrderRepository orders) {
		this.orders = orders;
	}

	public void processPlacedEvent(OrderPlacedEvent event) {
		LOG.info("Processing order {} - {}", event.id(), event.orderNumber());

		var order = orders.findById(event.id())
			.orElseThrow(() -> new RuntimeException("Order not found by ID " + event.id()));

		processOrder(order);

		orders.save(order);
		LOG.info("Order {} - {} processed with status {}", order.getId(), order.getOrderNumber(), order.getStatus());
	}

	private void processOrder(Order order) {
		if (order.getTotal() >= 1_000_00) {
			order.reject("Rejected by risk of fraud when total greater than $1,000.00");
			return;
		}
		if (orders.sumClientOrdersTotalLast30Days(order.getClient().getId()) > 5_000_00) {
			order.reject("Rejected by risk of fraud when customer orders total greater than $10,000.00 in the last month");
			return;
		}
		// TODO: verify if client has changed its email or address in the last week
		// TODO: verify if shipping address is the same from client registration
		order.setProcessed();
	}
}
