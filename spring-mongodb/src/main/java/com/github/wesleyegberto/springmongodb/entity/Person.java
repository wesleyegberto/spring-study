package com.github.wesleyegberto.springmongodb.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "People")
public class Person {
	@Id
	private UUID id;
	
	@NotBlank
	private String name;
	
	private LocalDateTime birthDate;
	
	public Person() {
	}
	
	public Person(String name) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.birthDate = LocalDateTime.now();
	}

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public LocalDateTime getBirthDate() {
		return birthDate;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
}
