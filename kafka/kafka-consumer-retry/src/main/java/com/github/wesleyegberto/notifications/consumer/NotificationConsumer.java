package com.github.wesleyegberto.notifications.consumer;

import java.io.IOException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.notifications.entity.Notification;
import com.github.wesleyegberto.notifications.service.NotificationSender;

@Component
public class NotificationConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(NotificationConsumer.class);
	
	private ObjectMapper objectMapper;
	private KafkaTemplate<String, String> kafkaTemplate;
	private NotificationSender sender;

	@Autowired
	public NotificationConsumer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate, NotificationSender sender) {
		this.objectMapper = objectMapper;
		this.kafkaTemplate = kafkaTemplate;
		this.sender = sender;
	}

	@KafkaListener(topics = "${kafka.notifications.topic.name}", errorHandler = "notificationErrorHandler", containerFactory = "kafkaListenerContainerFactory")
	public void onMessage(Message<String> kafkaMessage, Acknowledgment ack) {
		var message = kafkaMessage.getPayload();
		LOG.info("Message received: {}", message);
		
		// sleep();
		
		String errorChannel = new String((byte[]) kafkaMessage.getHeaders().getErrorChannel());
		LOG.info("Error Channel: {}", errorChannel);

		try {
			var notification = objectMapper.readValue(message, Notification.class);
			LOG.info("Ticket {} - Starting processing", notification.getId());
			sender.sendNotification(notification);
			
			// if we only commit at successful processing we would block the offset until the message is processed
			// we want here is to send the poisoned message to another topic and keep processing others messages
			// ack.acknowledge();
		} catch (IOException ex) {
			LOG.error("Error during parsing of notification {}: {}", message, ex.getMessage());
			this.sendToErrorChannel(kafkaMessage, errorChannel);
			throw new RuntimeException(ex);
		} catch (RuntimeException ex) {
			LOG.error("Error during processing of notification {}: {}", message, ex.getMessage());
			this.sendToErrorChannel(kafkaMessage, errorChannel);
			throw ex;
		} finally {
			// commiting even with error won't block the offset, thus the consumer will continue to process others messages
			ack.acknowledge();
		}
	}

	private void sleep() {
		LOG.info("Sleeping...");
		try {
			Thread.sleep(3_000);
		} catch (InterruptedException e) {
		}
	}

	private void sendToErrorChannel(Message<String> kafkaMessage, String errorChannel) {
		var errorMessage = new ProducerRecord<String, String>(errorChannel, kafkaMessage.getPayload());
		kafkaTemplate.send(errorMessage);
	}
}
