package com.github.wesleyegberto.orderinvoicer.business.order.invoicing.entity;

import java.math.BigDecimal;

public class Item {
	private String sku;
	private int quantity;
	private BigDecimal unitPrice;

	public String getSku() {
		return sku;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
}
