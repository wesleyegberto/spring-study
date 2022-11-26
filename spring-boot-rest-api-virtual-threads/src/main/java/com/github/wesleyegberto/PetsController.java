package com.github.wesleyegberto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets")
public class PetsController {
	@PostMapping
	public ResponseEntity<?> createPet(@RequestBody PetDto pet) {
		return ResponseEntity.ok(pet);
	}
}
