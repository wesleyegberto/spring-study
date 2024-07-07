package com.wesleyegberto.ecommerce.users;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	private LocalDateTime emailLastChangeDate;

	@Embedded
	@NotNull
	private Address address;
	private LocalDateTime addressLastChangeDate;

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

	private void setName(String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Invalid name");
		}
		this.name = name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	private void setBirthDate(LocalDate birthDate) {
		if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Invalid birth date");
		}
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("Invalid e-mail");
		}
		this.email = email;
		this.emailLastChangeDate = LocalDateTime.now();
	}

	public LocalDateTime getEmailLastChangeDate() {
		return emailLastChangeDate;
	}

	public Address getAddress() {
		if (address == null) {
			throw new IllegalArgumentException("Invalid address");
		}
		return address;
	}

	private void setAddress(Address address) {
		this.address = address;
		this.addressLastChangeDate = LocalDateTime.now();
	}

	public LocalDateTime getAddressLastChangeDate() {
		return addressLastChangeDate;
	}

	public void update(UserUpdateRequest updateRequest) {
		this.setName(updateRequest.name());
		this.setBirthDate(updateRequest.birthDate());
		if (updateRequest.hasEmail() && !this.email.equals(updateRequest.email())) {
			this.setEmail(updateRequest.email());
		}
		if (updateRequest.hasAddress() && !this.address.equals(updateRequest.address())) {
			this.setAddress(updateRequest.address());
		}
	}
}
