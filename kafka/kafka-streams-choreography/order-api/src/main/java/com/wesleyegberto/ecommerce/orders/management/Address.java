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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((street == null) ? 0 : street.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj instanceof Address other) {
			if (city == null) {
				if (other.city != null)
					return false;
			} else if (!city.equals(other.city)) {
				return false;
			}

			if (street == null) {
				if (other.street != null)
					return false;
			} else if (!street.equals(other.street)) {
				return false;
			}

			if (zipcode == null) {
				if (other.zipcode != null)
					return false;
			} else if (!zipcode.equals(other.zipcode)) {
				return false;
			}

			return true;
		}
		return false;
	}
}
