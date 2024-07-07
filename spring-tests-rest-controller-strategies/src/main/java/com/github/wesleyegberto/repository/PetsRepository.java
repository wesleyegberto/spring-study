package com.github.wesleyegberto.repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.github.wesleyegberto.entity.PetNotFoundException;
import com.github.wesleyegberto.entity.Pet;

import org.springframework.stereotype.Repository;

@Repository
public class PetsRepository {
	private List<Pet> petsDb;

	public PetsRepository() {
		this.petsDb = new LinkedList<>();
	}

	public void save(Pet pet) {
		this.petsDb.add(pet);
	}

	public Optional<Pet> findById(int id) {
		return this.petsDb.stream()
			.filter(p -> p.getId() == id)
			.findAny()
			.or(() -> {
				throw new PetNotFoundException();
			});
	}

	public List<Pet> findAll() {
		return Collections.unmodifiableList(this.petsDb);
	}

	public void deleteById(int id) {
		this.petsDb.removeIf(pet -> pet.getId() == id);
	}
}

