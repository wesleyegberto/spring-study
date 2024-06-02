package com.wesleyegberto.ecommerce.orders.management;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Embeddable
public class Client {
	@Min(1)
	@Column(name = "client_id")
	private long id;
	@NotEmpty
	@Column(name = "client_tax_id")
	private String taxId;
	@NotEmpty
	@Column(name = "client_name")
	private String name;
	@NotEmpty
	@Column(name = "client_email")
	private String email;

	public long getId() {
		return id;
	}

	public String getTaxId() {
		return taxId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}
