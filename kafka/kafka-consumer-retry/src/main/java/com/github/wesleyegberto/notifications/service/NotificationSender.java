package com.github.wesleyegberto.notifications.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.wesleyegberto.notifications.entity.Notification;

@Service
public class NotificationSender {
	private static final Logger LOG = LoggerFactory.getLogger(NotificationSender.class);

	public void sendNotification(Notification notification) {
		if (notification.getMessage().contains("ERROR")) {
			throw new IllegalArgumentException("Notification message contains ERROR");
		}
		LOG.info("Ticket {} - Sending e-mail with content: {}", notification.getId(), notification.getMessage());
	}
}
