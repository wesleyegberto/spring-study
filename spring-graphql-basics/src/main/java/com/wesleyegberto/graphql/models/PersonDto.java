package com.wesleyegberto.graphql.models;

import com.wesleyegberto.domain.movies.Person;

public class PersonDto {
	private String name;

	public PersonDto(String name) {
		this.name = name;
	}

	public static PersonDto from(Person person) {
		return new PersonDto(person.getName());
	}

	public String getName() {
		return name;
	}
}
