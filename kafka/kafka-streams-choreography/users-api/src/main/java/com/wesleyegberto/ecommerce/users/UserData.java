package com.wesleyegberto.ecommerce.users;

public class UserData {
	private long id;
	private String taxId;
	private String name;
	private String email;
	private Address address;

	public UserData(User user) {
		this.id = user.getId();
		this.taxId = user.getTaxId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.address = user.getAddress();
	}

	public static UserData of(User user) {
		return new UserData(user);
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

	public String getEmail() {
		return email;
	}

	public Address getAddress() {
		return address;
	}
}
