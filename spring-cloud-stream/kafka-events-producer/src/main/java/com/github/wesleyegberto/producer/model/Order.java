package com.github.wesleyegberto.producer.model;

import java.util.UUID;

public class Order {
	private UUID id;
	private String type;
	private int amount;

	public Order() {}

	public Order(String type, int amount) {
		this.id = UUID.randomUUID();
		this.type = type;
		this.amount = amount;
	}

	public static Order ofBuy(int amount) {
		return new Order("BUY", amount);
	}

	public static Order ofSell(int amount) {
		return new Order("SELL", amount);
	}

	public UUID getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return String.format("Order[id=%s, type=%s, amount=%d]", id, type, amount);
	}
}

