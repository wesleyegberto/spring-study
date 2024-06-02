package com.wesleyegberto.ecommerce.orders.placer;

import com.wesleyegberto.ecommerce.orders.management.Address;
import com.wesleyegberto.ecommerce.orders.management.Client;
import com.wesleyegberto.ecommerce.orders.management.Item;
import com.wesleyegberto.ecommerce.orders.management.Order;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class OrderPlaceRequest {
	@NotEmpty
	private String clientTaxId;
	@NotEmpty
	private List<com.wesleyegberto.ecommerce.orders.management.Item> items;
	@NotNull
	private Address shippingAddress;

	public String getClientTaxId() {
		return clientTaxId;
	}

	public List<Item> getItems() {
		return items;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public Order toEntity(Client client) {
		return Order.createFrom(items, client, shippingAddress);
	}
}
