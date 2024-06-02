package com.wesleyegberto.ecommerce.orders.management;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;

@Embeddable
public class Address {
	@NotEmpty
	@Column(name = "shipping_address_zipcode")
	private String zipcode;
	@NotEmpty
	@Column(name = "shipping_address_street")
	private String street;
	@NotEmpty
	@Column(name = "shipping_address_city")
	private String city;

	public String getZipcode() {
		return zipcode;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}
}

