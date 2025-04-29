package com.wesleyegberto.domain.movies;

import jakarta.persistence.Embeddable;

@Embeddable
public class Person {
	private String name;

	Person() {
	}

	public Person(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
