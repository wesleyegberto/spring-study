package com.wesleyegberto.ecommerce.users;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "users", indexes = @Index(columnList = "taxId", unique = true))
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotBlank
	@Column(updatable = false)
	private String taxId;
	@NotBlank
	private String name;
	@NotNull
	private LocalDate birthDate;

	@NotBlank
	@Email
	private String email;

	@Embedded
	@NotNull
	private Address address;

	User() {
	}

	public User(String taxId, String name, LocalDate birthDate, String email, Address address) {
		this.taxId = taxId;
		this.name = name;
		this.birthDate = birthDate;
		this.email = email;
		this.address = address;
	}

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

	public void update(UserUpdateRequest updateRequest) {
		this.name = updateRequest.name();
		this.birthDate = updateRequest.birthDate();
		this.email = updateRequest.email();
		this.address = updateRequest.address();
	}
}
