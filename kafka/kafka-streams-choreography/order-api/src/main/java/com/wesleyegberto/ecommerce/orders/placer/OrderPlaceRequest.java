package com.wesleyegberto.ecommerce.orders.placer;

import com.wesleyegberto.ecommerce.orders.management.Address;
import com.wesleyegberto.ecommerce.orders.management.Customer;
import com.wesleyegberto.ecommerce.orders.management.Item;
import com.wesleyegberto.ecommerce.orders.management.Order;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderPlaceRequest {
	@NotEmpty
	private String customerTaxId;
	@NotEmpty
	private List<Item> items;
	@NotNull
	private Address shippingAddress;

	public String getCustomerTaxId() {
		return customerTaxId;
	}

	public List<Item> getItems() {
		return items;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public Order toEntity(Customer customer) {
		return Order.createFrom(items, customer, shippingAddress);
	}
}
