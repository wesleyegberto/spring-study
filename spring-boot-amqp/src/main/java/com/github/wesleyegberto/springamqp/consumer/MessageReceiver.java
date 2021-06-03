package com.github.wesleyegberto.springamqp.consumer;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;

import com.github.wesleyegberto.springamqp.entity.Message;

@Component
public class MessageReceiver {
	public void handleMessage(Message message) {
		System.out.println(Thread.currentThread().getName() + " received <" + message + ">");
		try {
			Thread.sleep(1000 * ((long) (Math.random() * 5) + 1));
		} catch (InterruptedException e) {
			// Will not requeue
			throw new AmqpRejectAndDontRequeueException("Unhandled exception", e);
		}
	}
}
