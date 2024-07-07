package com.wesleyegberto.ecommerce.events;

import java.time.LocalDateTime;

import com.wesleyegberto.ecommerce.orders.management.Order;

public record OrderValidatedEvent(long id, String orderNumber, LocalDateTime eventDate) {
	public static OrderValidatedEvent of(Order order) {
		return new OrderValidatedEvent(order.getId(), order.getOrderNumber(), LocalDateTime.now());
	}
}
