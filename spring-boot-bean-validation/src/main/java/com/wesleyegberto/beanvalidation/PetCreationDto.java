package com.wesleyegberto.beanvalidation;

import jakarta.validation.constraints.NotEmpty;

public record PetCreationDto(
		@NotEmpty(message = "Name is required")
		String name,
		@NotEmpty(message = "Owner is required")
		String owner,
		@NotEmpty(message = "Breed is required")
		String breed) {

	public Pet toEntity() {
		return new Pet(name, owner, breed);
	}
}
