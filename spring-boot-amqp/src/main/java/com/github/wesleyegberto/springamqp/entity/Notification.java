package com.github.wesleyegberto.springamqp.entity;

import java.util.UUID;

public class Notification {
	private UUID id;
	private String notification;

	public Notification() {
	}

	public Notification(String message) {
		this.id = UUID.randomUUID();
		this.notification = message;
	}

	public UUID getId() {
		return id;
	}
	
	public String getNotification() {
		return notification;
	}

	@Override
	public String toString() {
		return "Notification [id=" + id + ", notification=" + notification + "]";
	}
}
