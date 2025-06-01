package com.wesleyegberto.threadpoolstarvation.pets;

import java.util.Collection;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/pets")
public class PetsController {
	private final PetService petService;

	PetsController(PetService petService) {
		this.petService = petService;
	}

	@GetMapping
	public Collection<Pet> getPets() {
		return petService.getAll();
	}

	@PostMapping
	public ResponseEntity<?> createPet(@RequestBody Pet pet) {
		var id = petService.save(pet);
		return ResponseEntity.ok(Map.of("id", id));
	}
}
