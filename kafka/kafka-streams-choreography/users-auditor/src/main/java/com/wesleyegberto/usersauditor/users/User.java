package com.wesleyegberto.usersauditor.users;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;

public class User {
	@Column(name = "user_id")
	private long id;
	private String taxId;
	private String name;
	private LocalDate birthDate;
	private String email;
	@Embedded
	private Address address;

	public long getId() {
		return id;
	}

	public String getTaxId() {
		return taxId;
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getEmail() {
		return email;
	}

	public Address getAddress() {
		return address;
	}
}
