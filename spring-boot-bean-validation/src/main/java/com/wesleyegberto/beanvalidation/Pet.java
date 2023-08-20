package com.wesleyegberto.beanvalidation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "pets")
public class Pet {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String owner;
	@NotEmpty
	private String breed;

	Pet() {}

	public Pet(String name, String owner, String breed) {
		this.name = name;
		this.owner = owner;
		this.breed = breed;
	}

	public int getId() {
	    return id;
	}

	public String getName() {
	    return name;
	}

	public String getOwner() {
	    return owner;
	}

	public String getBreed() {
	    return breed;
	}
}
