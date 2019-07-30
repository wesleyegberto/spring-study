package com.github.wesleyegberto.rpcserver.service;

public class NumberTooBigException extends RuntimeException {
	private static final long serialVersionUID = 7642533041671463656L;

	public NumberTooBigException(String message) {
		super(message);
	}
}
