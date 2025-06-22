package com.wesleyegberto.assistantbuyer.domain;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "products")
public class Product {
	@Id
	private int id;
	private String name;
	private String category;
	private BigDecimal price;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCategory() {
		return category;
	}

	public BigDecimal getPrice() {
		return price;
	}
}
