package com.github.wesleyegberto.consumer.model;

public class BuyOrder {
	private long id;
	private String type;
	private String product;
	private int amount;

	public BuyOrder() {}

	public long getId() {
		return id;
	}

	public String getProduct() {
		return product;
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

