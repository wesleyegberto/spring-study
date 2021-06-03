package com.github.wesleyegberto.springamqp.publisher;

import java.util.stream.IntStream;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.github.wesleyegberto.springamqp.entity.Message;
import com.github.wesleyegberto.springamqp.mq.MqProperties;

@Component
public class MessageRunnerPublisher implements CommandLineRunner {
	private MqProperties mqProps;
	private final RabbitTemplate rabbitTemplate;

	public MessageRunnerPublisher(MqProperties properties, RabbitTemplate rabbitTemplate) {
		this.mqProps = properties;
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Sending message...");
		IntStream.range(0, 5).forEach(this::publishMessage);
	}

	private void publishMessage(int i) {
		rabbitTemplate.convertAndSend(mqProps.getMqMessageExchange(), "", new Message("Message from Spring: " + i));
	}
}