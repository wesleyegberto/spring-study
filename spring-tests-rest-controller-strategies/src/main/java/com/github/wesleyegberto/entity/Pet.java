package com.github.wesleyegberto.entity;

public class Pet {
	private int id;
	private String name;
	private String owner;

	public Pet() {
	}

	public Pet(String name, String owner) {
		this.name = name;
		this.owner = owner;
	}

	public Pet(int id, String name, String owner) {
		this.id = id;
		this.name = name;
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getOwner() {
		return owner;
	}
}
