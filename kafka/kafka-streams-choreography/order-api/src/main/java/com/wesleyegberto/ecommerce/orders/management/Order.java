package com.wesleyegberto.ecommerce.orders.management;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders", indexes = {
		@Index(columnList = "orderNumber", unique = true),
		@Index(columnList = "customer.id")
})
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String orderNumber;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "orders_items")
	private List<Item> items;

	@Embedded
	private Customer customer;
	@Embedded
	private Address shippingAddress;

	private int total;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	private String message;

	Order() {
	}

	public Order(List<Item> items, Customer customer, Address shippingAddress) {
		this.createdAt = LocalDateTime.now();
		this.orderNumber = OrderNumberGenerator.generateOrderNumber();

		this.items = items;
		this.customer = customer;
		this.shippingAddress = shippingAddress;
		this.total = items.stream().mapToInt(Item::getUnitCost).sum();
		this.status = OrderStatus.PENDING;
	}

	public static Order createFrom(List<Item> items, Customer customer,
			Address shippingAddress) {
		return new Order(items, customer, shippingAddress);
	}

	public Long getId() {
		return id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public List<Item> getItems() {
		return items;
	}

	public Customer getCustomer() {
		return customer;
	}

	public long getCustomerId() {
		return customer.getId();
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public int getTotal() {
		return total;
	}

	public OrderStatus getStatus() {
		return status;
	}

	private void setStatus(OrderStatus status) {
		this.status = status;
		this.updatedAt = LocalDateTime.now();
	}

	public String getMessage() {
		return message;
	}

	public boolean isProcessed() {
		return this.status == OrderStatus.PROCESSED;
	}

	public void setProcessed() {
		setStatus(OrderStatus.PROCESSED);
	}

	public void reject(String message) {
		setStatus(OrderStatus.REJECTED);
		this.message = message;
	}

	public boolean isShippingToCustomerAddress(Address address) {
		return this.shippingAddress.equals(address);
	}
}
