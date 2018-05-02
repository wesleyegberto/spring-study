package com.github.wesleyegberto.springmongodb.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wesleyegberto.springmongodb.entity.Person;
import com.github.wesleyegberto.springmongodb.repository.ManualPersonRepository;

@RestController
@RequestMapping("/people")
public class PeopleController {
	@Autowired
	private ManualPersonRepository personRepository;
	
	@GetMapping
	public List<Person> getAll(String name) {
		return personRepository.findAll(name);
	}
	
	@GetMapping("{id}")
	public Person getById(@PathVariable("id") UUID id) {
		return personRepository.findById(id).orElse(null);
	}
	
	@PostMapping
	public Person create(@Valid @RequestBody Person person) {
		return personRepository.save(person);
	}
}
