package com.github.wesleyegberto.producer.model;

import java.util.concurrent.atomic.AtomicLong;

public class Order {
	private static final AtomicLong ID = new AtomicLong();

	private long id;
	private String type;
	private String product;
	private int amount;

	public Order() {}

	public Order(String type, String product, int amount) {
		this.id = ID.incrementAndGet();
		this.type = type;
		this.product = product;
		this.amount = amount;
	}

	public static Order ofBuy(String product, int amount) {
		return new Order("BUY", product, amount);
	}

	public static Order ofSell(String product, int amount) {
		return new Order("SELL", product, amount);
	}

	public long getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getProduct() {
		return product;
	}

	public int getAmount() {
		return amount;
	}

	@Override
	public String toString() {
		return String.format("Order[id=%s, type=%s, product=%s, amount=%d]", id, type, product, amount);
	}
}

