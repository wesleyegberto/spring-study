package com.wesleyegberto.ecommerce.orders.validator;

import com.wesleyegberto.ecommerce.events.OrderEventPublisher;
import com.wesleyegberto.ecommerce.events.OrderPlacedEvent;
import com.wesleyegberto.ecommerce.events.OrderValidatedEvent;
import com.wesleyegberto.ecommerce.orders.management.Order;
import com.wesleyegberto.ecommerce.orders.management.OrderRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderValidatorService {
	private static final Logger LOG = LoggerFactory.getLogger(OrderValidatorService.class);

	private final OrderRepository orders;
	private final OrderEventPublisher eventPublisher;

	public OrderValidatorService(OrderRepository orders, OrderEventPublisher eventPublisher) {
		this.orders = orders;
		this.eventPublisher = eventPublisher;
	}

	public void processOrderPlacedEvent(OrderPlacedEvent event) {
		LOG.info("Validating order {} - {}", event.id(), event.orderNumber());

		var order = orders.findById(event.id())
			.orElseThrow(() -> new RuntimeException("Order not found by ID " + event.id()));

		validateOrder(order);

		orders.save(order);

		if (order.isProcessed()) {
			this.eventPublisher.notifyValidatedOrder(OrderValidatedEvent.of(order));
		}

		LOG.info("Order {} - {} validated with status {}", order.getId(), order.getOrderNumber(), order.getStatus());
	}

	private void validateOrder(Order order) {
		if (order.getTotal() >= 1_000_00) {
			order.reject("Rejected by risk of fraud when total greater than $1,000.00");
			return;
		}
		if (orders.sumCustomerOrdersTotalLast30Days(order.getCustomer().getId()) > 5_000_00) {
			order.reject("Rejected by risk of fraud when customer orders total greater than $10,000.00 in the last month");
			return;
		}
		order.setProcessed();
	}
}
