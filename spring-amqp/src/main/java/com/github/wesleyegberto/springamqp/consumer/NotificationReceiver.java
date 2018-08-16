package com.github.wesleyegberto.springamqp.consumer;

import org.springframework.stereotype.Component;

import com.github.wesleyegberto.springamqp.entity.Notification;

@Component
public class NotificationReceiver {
	public void handleMessage(Notification notification) {
		System.out.println(Thread.currentThread().getName() + " received <" + notification + ">");
		try {
			Thread.sleep(1000 * ((long) (Math.random() * 5) + 1));
		} catch (InterruptedException e) {
		}
	}
}
