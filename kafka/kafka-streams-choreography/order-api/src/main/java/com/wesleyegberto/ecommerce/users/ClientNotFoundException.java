package com.wesleyegberto.ecommerce.users;


public class ClientNotFoundException extends RuntimeException {
	public ClientNotFoundException(String message) {
		super(message);
	}
}
