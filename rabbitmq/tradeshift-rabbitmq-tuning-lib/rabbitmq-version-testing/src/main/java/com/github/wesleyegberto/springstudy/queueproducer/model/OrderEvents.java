package com.github.wesleyegberto.springstudy.queueproducer.model;

public interface OrderEvents {
	String RECEIVED = "order-received";
	String PROCESSED = "order-processed";
	String INVOICED = "order-invoiced";
}
