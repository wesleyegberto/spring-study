package com.wesleyegberto.ecommerce.users;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreationRequest(
		@NotBlank String taxId,
		@NotBlank String name,
		@NotNull LocalDate birthDate,
		@NotBlank @Email String email,
		@Valid @NotNull Address address) {
	public User toEntity() {
		return new User(taxId, name, birthDate, email, address);
	}
}
