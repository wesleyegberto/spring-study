package com.github.wesleyegberto.rpcclient.service;

public class NonResponseException extends RuntimeException {
	private static final long serialVersionUID = -522020266219543360L;

	public NonResponseException(String message) {
		super(message);
	}

}
