package com.github.wesleyegberto.kafkaschemas.people;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
	private String postalCode;
	private String street;
	private boolean active;

	public String getPostalCode() {
		return postalCode;
	}

	public String getStreet() {
		return street;
	}

	public boolean isActive() {
		return active;
	}
}
