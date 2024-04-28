package com.wesleyegberto.modulithevents;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class Order {
	private Long id;
	private LocalDateTime creationDate;
	private String customerTaxId;
	private BigDecimal total;

	public Long getId() {
		return id;
	}

	public LocalDateTime getCreationDate() {
	    return creationDate;
	}

	public String getCustomerTaxId() {
		return customerTaxId;
	}

	public BigDecimal getTotal() {
		return total;
	}

	void setCreated(Long id) {
		this.id = id;
		this.creationDate = LocalDateTime.now(ZoneOffset.UTC);
	}
}
