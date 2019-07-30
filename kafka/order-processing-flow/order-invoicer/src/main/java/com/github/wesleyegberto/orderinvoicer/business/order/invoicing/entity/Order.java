package com.github.wesleyegberto.orderinvoicer.business.order.invoicing.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {
	private UUID id;
	private String number;
	private LocalDateTime creationDate;
	
	private String clientDocument;
	private BigDecimal total;
	private List<Item> items;
	private Status status;
	
	public UUID getId() {
		return id;
	}
	
	public String getNumber() {
		return number;
	}
	
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public String getClientDocument() {
		return clientDocument;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public List<Item> getItems() {
		return items;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setCreated(UUID id, String number) {
		this.id = id;
		this.number = number;
		this.status = Status.Created;
		this.creationDate = LocalDateTime.now();
	}
}
