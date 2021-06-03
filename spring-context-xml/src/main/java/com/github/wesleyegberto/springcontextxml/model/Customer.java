package com.github.wesleyegberto.springcontextxml.model;

public class Customer {
	private Long id;
	private String name;

	public Customer() {
	}

	public Customer(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
