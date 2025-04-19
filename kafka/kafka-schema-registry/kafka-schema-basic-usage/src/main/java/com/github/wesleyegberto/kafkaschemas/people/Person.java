package com.github.wesleyegberto.kafkaschemas.people;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "People")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate birthDate;

	@Embedded
	private Address address;
	private List<String> emails;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public Address getAddress() {
		return address;
	}

	public List<String> getEmails() {
		return emails;
	}
}
