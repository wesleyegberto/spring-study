package com.github.wesleyegberto.ordercreator.api.model;

import java.util.UUID;

public class ApiError {
	private UUID ticket;
	private String message;

	public ApiError(String message) {
		this.ticket = UUID.randomUUID();
		this.message = message;
	}

	public static ApiError fromException(Exception ex) {
		return new ApiError(ex.getMessage());
	}

	public UUID getTicket() {
		return ticket;
	}

	public String getMessage() {
		return message;
	}
}
