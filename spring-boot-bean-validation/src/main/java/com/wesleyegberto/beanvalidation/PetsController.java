package com.wesleyegberto.beanvalidation;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pets")
public class PetsController {
    private static final Logger LOG = LoggerFactory.getLogger(PetsController.class);

	private final PetsRepository petsRepository;

	PetsController(PetsRepository petsRepository) {
		this.petsRepository = petsRepository;}

	@GetMapping
	public ResponseEntity<Stream<PetDto>> findAll() {
		var pets = StreamSupport.stream(petsRepository.findAll().spliterator(), false)
			.map(PetDto::new);
		return ResponseEntity.ok(pets);
	}

	@PostMapping
	public ResponseEntity<?> savePet(@RequestBody @Valid PetCreationDto pet) {
		LOG.info("Saving pet {}", pet.name());
		petsRepository.save(pet.toEntity());
		return ResponseEntity.ok().build();
	}
}
