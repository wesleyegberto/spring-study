package com.github.wesleyegberto.ordercreator.business.order.creation.service;

import java.util.List;

import com.github.wesleyegberto.ordercreator.business.order.creation.entity.Item;
import com.github.wesleyegberto.ordercreator.business.order.creation.entity.Order;

public class OrderValidator {
	public static void validate(Order order) { 
		var message = validateAndReturnError(order);
		if (message == null)
			return;
		throw new OrderCreationException(message);
	}
	
	private static String validateAndReturnError(Order order) {
		if (order == null)
			return "Order must be non-null";
		
		if (order.getClientDocument() == null)
		    return "Client's document is required";
		if (order.getItems() == null)
		    return "Order must have at least one item";
		if (order.getTotal() == null)
		    return "Total must be calculated";

		if (order.getItems().isEmpty())
			return "Order must have at least one item";
		
		return validateItems(order.getItems());
	}
	
	private static String validateItems(List<Item> items) {
		for (Item item : items) {
			if (item == null)
				return "Order has an invalid item";
			if (item.getSku() == null)
				return "SKU is required";
			if (item.getQuantity() < 1)
				return "Quantity must be informed for SKU " + item.getSku();
			if (item.getUnitPrice() == null)
				return "SKU " + item.getSku() + " has invalid price";
		}
		return null;
	}
}
