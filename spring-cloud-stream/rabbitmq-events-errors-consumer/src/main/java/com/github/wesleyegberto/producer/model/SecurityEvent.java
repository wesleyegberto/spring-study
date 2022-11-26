package com.github.wesleyegberto.producer.model;

import java.util.UUID;

public class SecurityEvent {
	private UUID id;
	private String type;
	private String message;

	public SecurityEvent() {}

	public SecurityEvent(String type, String message) {
		this.id = UUID.randomUUID();
		this.type = type;
		this.message = message;
	}

	public static SecurityEvent ofAlert(String message) {
		return new SecurityEvent("ALERT", message);
	}

	public static SecurityEvent ofInformation(String message) {
		return new SecurityEvent("INFORMATION", message);
	}

	public static SecurityEvent ofAction(String message) {
		return new SecurityEvent("ACTION", message);
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

	 @Override
	 public String toString() {
		return String.format("SecurityEvent[id=%s, type=%s, message=%s]", id, type, message);
	 }
}

