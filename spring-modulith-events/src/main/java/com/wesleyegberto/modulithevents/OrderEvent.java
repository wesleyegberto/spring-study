package com.wesleyegberto.modulithevents;

public record OrderEvent(OrderEventType eventType, Long orderId) {
	public static OrderEvent created(Order order) {
		return new OrderEvent(OrderEventType.CREATED, order.getId());
	}
}

enum OrderEventType {
	CREATED
}
