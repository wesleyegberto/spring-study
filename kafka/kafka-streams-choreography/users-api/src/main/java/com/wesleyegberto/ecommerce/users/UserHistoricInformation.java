package com.wesleyegberto.ecommerce.users;

import java.time.LocalDateTime;

public record UserHistoricInformation(
		LocalDateTime lastDateEmailChanged,
		LocalDateTime lastDateAddressChanged,
		Address address) {

	public static UserHistoricInformation of(User user) {
		return new UserHistoricInformation(user.getEmailLastChangeDate(), user.getAddressLastChangeDate(),
				user.getAddress());
	}
}
