package com.wesleyegberto.ecommerce.users;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserUpdateRequest(
		@NotBlank String name,
		@NotNull LocalDate birthDate,
		@NotBlank @Email String email,
		@Valid @NotNull Address address) {

	public boolean hasEmail() {
		return email != null && !email.isBlank();
	}

	public boolean hasAddress() {
		return address != null;
	}
}
