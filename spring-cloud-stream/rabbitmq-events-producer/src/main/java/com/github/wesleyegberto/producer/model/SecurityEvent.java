package com.github.wesleyegberto.producer.model;

import java.util.UUID;

public class SecurityEvent {
	private UUID id;
	private String type;
	private String region;
	private String message;

	public SecurityEvent() {}

	public SecurityEvent(String type, String region, String message) {
		this.id = UUID.randomUUID();
		this.type = type;
		this.region = region;
		this.message = message;
	}

	public static SecurityEvent ofAlert(String message) {
		return new SecurityEvent("ALERT", null, message);
	}

	public static SecurityEvent ofInformation(String message) {
		return new SecurityEvent("INFORMATION", null, message);
	}

	public static SecurityEvent ofRegion(String region, String message) {
		return new SecurityEvent("ALERT", region, message);
	}

	public UUID getId() {
		return id;
	}

	public String getRegion() {
		return region;
	}

	public String getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}
}

