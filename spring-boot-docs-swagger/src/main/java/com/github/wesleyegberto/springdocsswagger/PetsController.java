package com.github.wesleyegberto.springdocsswagger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/pets")
public class PetsController {
	private static final AtomicInteger ID = new AtomicInteger();
	private Map<Integer, Pet> pets = new LinkedHashMap<>();

	@GetMapping
	@ApiOperation(value = "Get Pets", produces = MediaType.APPLICATION_JSON_VALUE)
	public Collection<Pet> getPets() {
		return pets.values();
	}

	@PostMapping
	@ApiOperation(value = "Create a Pet", produces = MediaType.APPLICATION_JSON_VALUE)
	public Pet createPet(@RequestBody Pet pet) {
		pet.setId(ID.incrementAndGet());
		this.pets.put(pet.getId(), pet);
		return pet;
	}

	@GetMapping("{id}")
	@ApiOperation(value = "Get a Pet by ID", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pet> getPetById(@PathVariable int id) {
		if (this.pets.containsKey(id)) {
			return ResponseEntity.ok(this.pets.get(id));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("{id}")
	@ApiOperation(value = "Update a Pet", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pet> updatePet(@PathVariable int id, @RequestBody Pet pet) {
		if (!this.pets.containsKey(id)) {
			return ResponseEntity.notFound().build();
		}
		pets.put(pet.getId(), pet);
		return ResponseEntity.ok(pet);
	}

	@DeleteMapping("{id}")
	@ApiOperation(value = "Update a Pet by ID", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pet> deletePet(@PathVariable int id) {
		if (this.pets.containsKey(id)) {
			this.pets.remove(id);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}

class Pet {
	private int id;
	private LocalDateTime createDate;
	private String name;
	private String breed;
	private LocalDate birthDate;
	private Owner owner;

	public int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public String getName() {
		return name;
	}

	public String getBreed() {
		return breed;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public Owner getOwner() {
		return owner;
	}
}

class Owner {
	private String name;
	private String email;

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
}