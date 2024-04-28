package com.wesleyegberto.ecommerce.users;

import jakarta.validation.constraints.NotBlank;

public class Address {
	@NotBlank
	private String zipcode;
	@NotBlank
	private String street;
	@NotBlank
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
