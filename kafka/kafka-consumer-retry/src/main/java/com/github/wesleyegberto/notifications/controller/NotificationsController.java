package com.github.wesleyegberto.notifications.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wesleyegberto.notifications.entity.Notification;
import com.github.wesleyegberto.notifications.entity.NotificationTicket;
import com.github.wesleyegberto.notifications.service.NotificatorEnqueuer;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {
	@Autowired
	private NotificatorEnqueuer notificator;

	@PostMapping
	public NotificationTicket notify(@RequestBody  Notification notification) {
		return this.notificator.notify(notification);
	}
}
