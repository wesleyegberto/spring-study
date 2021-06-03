package com.github.wesleyegberto.spring.springredistest.datastore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets")
public class PetsController {
	@Autowired
	private PetRedisRepository petRepository;

	@PostMapping
	public void createPet(@RequestBody Pet pet) {
		petRepository.save(pet);
	}
}
