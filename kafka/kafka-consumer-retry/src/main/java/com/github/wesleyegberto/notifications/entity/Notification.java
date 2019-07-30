package com.github.wesleyegberto.notifications.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
	private UUID id;
	private LocalDateTime createDate;
	private String message;
	
	public UUID getId() {
		return id;
	}
	
	public LocalDateTime getCreateDate() {
		return createDate;
	}
	
	public String getMessage() {
		return message;
	}

	public void receive() {
		if (this.id != null) {
			throw new IllegalStateException("Notification already received. Ticket " + id);
		}
		
		this.id = UUID.randomUUID();
		this.createDate = LocalDateTime.now();
	}
}
