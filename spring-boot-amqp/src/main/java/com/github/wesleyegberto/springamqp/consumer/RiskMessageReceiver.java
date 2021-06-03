package com.github.wesleyegberto.springamqp.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RiskMessageReceiver {
	
	public RiskMessageReceiver() {
		System.out.println("Starting RiskMessageReceiver");
	}

	@RabbitListener(containerFactory = "customListenerContainerFactory", queues = "${mq.poison-message.queue}", returnExceptions = "false")
	public void handleMessage(@Payload String message) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " received Risk <" + message + ">");
		if (Math.random() > 0.5) {
			System.out.println("-- Will throw random error...");
			Thread.sleep(1000 * ((long) (Math.random() * 5)));
			throw new RuntimeException("Random error");
		}
		System.out.println("-- Will ack");
		Thread.sleep(1000 * ((long) (Math.random() * 5)));
		System.out.println("-- Acked");
	}
}
