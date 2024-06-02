package com.wesleyegberto.ecommerce.orders.management;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Embeddable
public class Item {
	@NotEmpty
	private String skuCode;
	@Min(1)
	private short quantity;
	@Min(1)
	private int unitCost;

	public String getSkuCode() {
		return skuCode;
	}

	public short getQuantity() {
		return quantity;
	}

	public int getUnitCost() {
		return unitCost;
	}
}
