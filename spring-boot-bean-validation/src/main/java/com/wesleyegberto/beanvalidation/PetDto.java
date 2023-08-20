package com.wesleyegberto.beanvalidation;

public record PetDto(
		int id,
		String name,
		String owner,
		String breed) {

	public PetDto(Pet pet) {
		this(pet.getId(), pet.getName(), pet.getOwner(), pet.getBreed());
	}
}
