package com.github.wesleyegberto.consumer.model;

import java.util.concurrent.atomic.AtomicLong;

public class Transaction {
	private static final AtomicLong ID = new AtomicLong();

	private long id;
	private String product;
	private long buyOrderId;
	private long sellOrderId;
	private int amount;
	private int amountAbove;

	public Transaction() {}

	public Transaction(Order buyOrder, Order sellOrder) {
		if (buyOrder.getAmount() < sellOrder.getAmount())
			throw new IllegalStateException("Cannot buy product with a lower price than requested.");
		this.id = ID.incrementAndGet();
		this.product = buyOrder.getProduct();
		this.buyOrderId = buyOrder.getId();
		this.sellOrderId = sellOrder.getId();
		this.amount = buyOrder.getAmount();
		this.amountAbove = buyOrder.getAmount() - sellOrder.getAmount();
	}

	public static Transaction ofOrders(Order buyOrder, Order sellOrder) {
		return new Transaction(buyOrder, sellOrder);
	}

	public long getId() {
		return id;
	}

	public String getProduct() {
		return product;
	}

	public long getBuyOrderId() {
		return buyOrderId;
	}

	public long getSellOrderId() {
		return sellOrderId;
	}

	public int getAmount() {
		return amount;
	}

	public int getAmountAbove() {
		return amountAbove;
	}

	@Override
	public String toString() {
		return String.format("Transaction[id=%d, product=%s, buy=%d, sell=%d, amount=%d, above=%d]",
				id, product, buyOrderId, sellOrderId, amount, amountAbove);
	}
}
