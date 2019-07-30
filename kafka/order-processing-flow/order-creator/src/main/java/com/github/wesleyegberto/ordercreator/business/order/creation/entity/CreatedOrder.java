package com.github.wesleyegberto.ordercreator.business.order.creation.entity;

import java.util.UUID;

public class CreatedOrder {
	private UUID id;
	private String number;

	public CreatedOrder(UUID id, String number) {
		this.id = id;
		this.number = number;
	}

	public static CreatedOrder from(Order order) {
		return new CreatedOrder(order.getId(), order.getNumber());
	}

	public UUID getId() {
		return id;
	}

	public String getNumber() {
		return number;
	}
}
