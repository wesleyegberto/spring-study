package com.wesleyegberto.ecommerce.events;

import com.wesleyegberto.ecommerce.orders.management.Order;

import java.time.LocalDateTime;

public record OrderPlacedEvent(
	long id,
	String orderNumber,
	LocalDateTime eventDate
) {
	public static OrderPlacedEvent of(Order order) {
		return new OrderPlacedEvent(order.getId(), order.getOrderNumber(), order.getCreatedAt());
	}
}
