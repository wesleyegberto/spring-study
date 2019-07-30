package com.github.wesleyegberto.notifications.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wesleyegberto.notifications.entity.Notification;
import com.github.wesleyegberto.notifications.entity.NotificationTicket;
import com.github.wesleyegberto.notifications.producer.NotificationProducer;

@Service
public class NotificatorEnqueuer {
	@Autowired
	private NotificationProducer kafkaProducer;

	public NotificationTicket notify(Notification notification) {
		notification.receive();
		
		kafkaProducer.publish(notification);
		
		return NotificationTicket.fromNotification(notification);
	}
}
