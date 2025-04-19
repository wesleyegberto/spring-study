package com.github.wesleyegberto.kafkaschemas.people;

import java.util.Map;

import com.github.wesleyegberto.kafkaschemas.producer.PersonRegisteredProducer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/person")
public class PersonController {

	private final PersonRepository personRepository;
	private final PersonRegisteredProducer registeredProducer;

	PersonController(PersonRepository personRepository, PersonRegisteredProducer registeredProducer) {
		this.personRepository = personRepository;
		this.registeredProducer = registeredProducer;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Person person) {
		try {
			var saved = personRepository.save(person);
			registeredProducer.notifyRegistration(saved);

			return ResponseEntity.ok(saved);

		} catch (RuntimeException e) {
			return ResponseEntity.internalServerError().body(Map.of("error", e.getMessage()));
		}
	}

}
