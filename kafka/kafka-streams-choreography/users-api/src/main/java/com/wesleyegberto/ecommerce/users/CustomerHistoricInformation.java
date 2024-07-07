package com.wesleyegberto.ecommerce.users;

import java.time.LocalDateTime;

public class CustomerHistoricInformation {
	private LocalDateTime lastDateEmailChanged;
	private LocalDateTime lastDateAddressChanged;

	private Address address;

	public LocalDateTime getLastDateEmailChanged() {
		return lastDateEmailChanged;
	}

	public LocalDateTime getLastDateAddressChanged() {
		return lastDateAddressChanged;
	}

	public Address getAddress() {
		return address;
	}
}
