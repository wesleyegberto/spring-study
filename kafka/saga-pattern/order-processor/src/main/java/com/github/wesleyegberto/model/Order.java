package com.github.wesleyegberto.model;

public class Order {
	private int id;
	private int customerId;
	private int productId;
	private int productCount;
	private double price;
	private String status;
	private String source;

	public Order() {}

	public Order(int id, int customerId, int productId, int productCount, double price) {
		this.id = id;
		this.customerId = customerId;
		this.productId = productId;
		this.productCount = productCount;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
