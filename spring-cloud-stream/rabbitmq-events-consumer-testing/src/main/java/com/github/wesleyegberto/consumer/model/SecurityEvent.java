package com.github.wesleyegberto.consumer.model;

import java.util.UUID;

public class SecurityEvent {
	private UUID id;
	private String type;
	private String message;

	public SecurityEvent() {}

	public SecurityEvent(UUID id, String type, String message) {
		this.id = id;
		this.type = type;
		this.message = message;
	}

	public UUID getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}
}

