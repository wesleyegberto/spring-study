package com.wesleyegberto.ecommerce.orders.placer;

import com.wesleyegberto.ecommerce.orders.management.Order;
import com.wesleyegberto.ecommerce.orders.management.OrderStatus;

import java.time.LocalDateTime;

public class PlacedOrder {
	private String orderNumber;
	private LocalDateTime createdAt;
	private int total;
	private OrderStatus status;

	public PlacedOrder(String orderNumber, LocalDateTime createdAt, int total, OrderStatus status) {
		this.orderNumber = orderNumber;
		this.createdAt = createdAt;
		this.total = total;
		this.status = status;
	}

	public static PlacedOrder of(Order order) {
		return new PlacedOrder(order.getOrderNumber(), order.getCreatedAt(), order.getTotal(), order.getStatus());
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public int getTotal() {
		return total;
	}

	public OrderStatus getStatus() {
		return status;
	}
}
