package com.wesleyegberto.ecommerce.orders.processing;

import com.wesleyegberto.ecommerce.events.OrderValidatedEvent;
import com.wesleyegberto.ecommerce.orders.management.Order;
import com.wesleyegberto.ecommerce.orders.management.OrderRepository;
import com.wesleyegberto.ecommerce.users.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderProcessorService {
	private static final Logger LOG = LoggerFactory.getLogger(OrderProcessorService.class);

	private final OrderRepository orders;
	private final UserRepository users;

	public OrderProcessorService(OrderRepository orders, UserRepository users) {
		this.orders = orders;
		this.users = users;
	}

	public void processOrderValidatedEvent(OrderValidatedEvent event) {
		LOG.info("Processing order {} - {}", event.id(), event.orderNumber());

		var order = orders.findById(event.id())
				.orElseThrow(() -> new RuntimeException("Order not found by ID " + event.id()));

		verifyCustomer(order);

		LOG.info("Order {} - {} processed with status {}", order.getId(), order.getOrderNumber(), order.getStatus());
	}

	private void verifyCustomer(Order order) {
		// verify if customer has changed the email or address in the last week
		var customerHistoric = users.getHistoricByCustomerId(order.getCustomerId());
		if (customerHistoric.changedEmailInLastWeek()) {
			order.reject("Customer has changed the e-mail in the last week.");
		}
		if (customerHistoric.changedAddressInLastWeek()) {
			order.reject("Customer has changed the address in the last week.");
		}
		// verify if shipping address is the same from customer registration
		if (!order.isShippingToCustomerAddress(customerHistoric.getAddress())) {
			order.reject("Order is not shipping to verified customer address.");
		}
	}
}
