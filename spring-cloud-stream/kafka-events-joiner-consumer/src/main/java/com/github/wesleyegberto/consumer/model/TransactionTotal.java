package com.github.wesleyegberto.consumer.model;

public class TransactionTotal {
	private int count;
	private int productCount;
	private int amount;

	public int getCount() {
		return count;
	}

	public int getProductCount() {
		return productCount;
	}

	public int getAmount() {
		return amount;
	}

	public void registerAmount(int amount) {
		this.count = this.count + 1;
		this.productCount = this.productCount + 1;
		this.amount += amount;
	}

	 @Override
	 public String toString() {
		 return String.format("TransactionTotal[count=%d, productCount=%d, amount=%d]", productCount, count, amount);
	 }
}
