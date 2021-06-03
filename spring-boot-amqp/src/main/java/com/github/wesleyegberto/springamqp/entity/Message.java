package com.github.wesleyegberto.springamqp.entity;

import java.util.UUID;

public class Message {
	private UUID id;
	private String message;

	public Message() {
	}

	public Message(String message) {
		this.id = UUID.randomUUID();
		this.message = message;
	}

	public UUID getId() {
		return id;
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + "]";
	}
}
