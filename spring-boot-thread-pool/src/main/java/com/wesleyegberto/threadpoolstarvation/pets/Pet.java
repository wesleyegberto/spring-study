package com.wesleyegberto.threadpoolstarvation.pets;

public class Pet {
	private long id;
	private String name;
	private String breed;
	private String owner;

	public long getId() {
		return id;
	}

	void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getBreed() {
		return breed;
	}

	public String getOwner() {
		return owner;
	}
}
