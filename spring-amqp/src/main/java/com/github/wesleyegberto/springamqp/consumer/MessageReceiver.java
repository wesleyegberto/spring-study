package com.github.wesleyegberto.springamqp.consumer;

import org.springframework.stereotype.Component;

import com.github.wesleyegberto.springamqp.entity.Message;

@Component
public class MessageReceiver {
	public void handleMessage(Message message) {
		System.out.println("Received <" + message + ">");
		try {
			Thread.sleep(1000 * ((long) (Math.random() * 5) + 1));
		} catch (InterruptedException e) {
		}
	}
}
