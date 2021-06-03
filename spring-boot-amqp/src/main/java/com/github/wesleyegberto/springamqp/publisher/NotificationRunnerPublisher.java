package com.github.wesleyegberto.springamqp.publisher;

import java.util.stream.IntStream;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.wesleyegberto.springamqp.entity.Notification;
import com.github.wesleyegberto.springamqp.mq.MqProperties;

@Component
public class NotificationRunnerPublisher implements CommandLineRunner {
	private MqProperties mqProps;
	private final RabbitTemplate rabbitTemplate;

	public NotificationRunnerPublisher(MqProperties properties, RabbitTemplate rabbitTemplate) {
		this.mqProps = properties;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sending notification...");
		IntStream.range(0, 10).forEach(this::publishNotification);
	}

	private void publishNotification(int i) {
		rabbitTemplate.convertAndSend(mqProps.getMqNotificationExchange(), "", new Notification("Notification from: " + i));
	}
}