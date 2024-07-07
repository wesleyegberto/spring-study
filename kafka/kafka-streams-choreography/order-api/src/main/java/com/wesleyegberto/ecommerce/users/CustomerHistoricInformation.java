package com.wesleyegberto.ecommerce.users;

import java.time.LocalDateTime;

import com.wesleyegberto.ecommerce.orders.management.Address;

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

	public boolean changedEmailInLastWeek() {
		return lastDateEmailChanged != null && LocalDateTime.now().minusDays(7).isBefore(lastDateEmailChanged);
	}

	public boolean changedAddressInLastWeek() {
		return lastDateAddressChanged != null && LocalDateTime.now().minusDays(7).isBefore(lastDateAddressChanged);
	}
}
