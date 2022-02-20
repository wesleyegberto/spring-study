package com.github.wesleyegberto.producer.model;

import java.util.UUID;

public class SecurityEvent {
	private UUID id;
	private String type;
	private String region;
	private String message;

	public SecurityEvent() {}

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

