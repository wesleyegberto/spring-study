package com.github.wesleyegberto.springstudy.queueproducer.messaging.processing;

public class SkuOutOfStockException extends RuntimeException {
	private static final long serialVersionUID = 8336814467452012707L;

	public SkuOutOfStockException(String message) {
		super(message);
	}
}
