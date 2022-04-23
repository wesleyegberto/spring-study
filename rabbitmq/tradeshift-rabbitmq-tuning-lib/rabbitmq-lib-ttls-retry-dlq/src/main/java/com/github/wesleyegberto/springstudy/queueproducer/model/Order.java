package com.github.wesleyegberto.springstudy.queueproducer.model;

import java.time.LocalDateTime;

public class Order {
	private int orderNumber;
	private LocalDateTime orderDate;
	private String sku;
	private int total;

	private LocalDateTime processingDate;
	private LocalDateTime invoiceDate;

	Order() {
	}

	public Order(int orderNumber, int total) {
		this.orderNumber = orderNumber;
		this.orderDate = LocalDateTime.now();
		if (orderNumber % 4 == 0) {
			this.sku = "XPTO-0";
		} else if (orderNumber % 5 == 0) {
			this.sku = "XPTO-2";
		} else {
			this.sku = "XPTO-13";
		}
		this.total = total;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public String getSku() {
		return sku;
	}

	public long getTotal() {
		return total;
	}

	public LocalDateTime getProcessingDate() {
		return processingDate;
	}

	public LocalDateTime getInvoiceDate() {
		return invoiceDate;
	}

	public void setProcessed() {
		this.processingDate = LocalDateTime.now();
	}

	public void setInvoiced() {
		this.invoiceDate = LocalDateTime.now();
	}
}