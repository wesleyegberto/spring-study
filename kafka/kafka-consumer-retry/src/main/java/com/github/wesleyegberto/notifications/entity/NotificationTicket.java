package com.github.wesleyegberto.notifications.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class NotificationTicket {
	private UUID ticket;
	private LocalDateTime createDate;

	public NotificationTicket(UUID ticket, LocalDateTime createDate) {
		this.ticket = ticket;
		this.createDate = createDate;
	}

	public static NotificationTicket fromNotification(Notification notification) {
		return new NotificationTicket(notification.getId(), notification.getCreateDate());
	}

	public UUID getTicket() {
		return ticket;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}
}
