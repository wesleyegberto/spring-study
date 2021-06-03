package com.github.wesleyegberto.reactiveweb.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.wesleyegberto.reactiveweb.entity.Person;
import com.github.wesleyegberto.reactiveweb.repository.PersonRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping("/people")
	public Flux<Person> findAll(@RequestParam(required = false) String name) {
		if(name == null || name.isEmpty())
			return this.personRepository.findAll();
		return this.personRepository.findByName(name);
	}

	@GetMapping("/people/{id}")
	public Mono<Person> findAll(@PathVariable UUID id) {
		return this.personRepository.findById(id);
	}
	
	@PostMapping("/people")
	public Mono<Person> save(@RequestBody Person person) {
		person.setId(UUID.randomUUID());
		return personRepository.insert(person);
	}
}
