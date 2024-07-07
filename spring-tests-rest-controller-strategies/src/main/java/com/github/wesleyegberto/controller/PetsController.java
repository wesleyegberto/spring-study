package com.github.wesleyegberto.controller;

import java.util.List;
import java.util.Optional;

import com.github.wesleyegberto.entity.Pet;
import com.github.wesleyegberto.repository.PetsRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets")
public class PetsController {
	private final PetsRepository petsRepository;

	public PetsController(PetsRepository petsRepository) {
		this.petsRepository = petsRepository;
	}

	@GetMapping
	public List<Pet> getAll() {
		return this.petsRepository.findAll();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createPet(@RequestBody Pet pet) {
		this.petsRepository.save(pet);
	}

	@GetMapping("{id}")
	public Optional<Pet> getById(@PathVariable int id) {
		return this.petsRepository.findById(id);
	}

	@DeleteMapping("{id}")
	public void deleteById(@PathVariable int id) {
		this.petsRepository.deleteById(id);
	}

}

