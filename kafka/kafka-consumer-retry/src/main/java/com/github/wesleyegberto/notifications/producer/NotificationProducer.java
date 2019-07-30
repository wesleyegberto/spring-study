package com.github.wesleyegberto.notifications.producer;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.notifications.entity.Notification;

@Component
public class NotificationProducer {
	private static final Logger LOG = LoggerFactory.getLogger(NotificationProducer.class);

	private ObjectMapper objectMapper;
	private KafkaTemplate<String, String> kafkaTemplate;
	private String notificationsTopicName;
	private String notificationsErrorChannel;

	@Autowired
	public NotificationProducer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate,
			@Value("${kafka.notifications.topic.name}") String notificationsTopicName,
			@Value("${kafka.notifications.error-channel}") String notificationsErrorChannel) {
		this.objectMapper = objectMapper;
		this.kafkaTemplate = kafkaTemplate;
		this.notificationsTopicName = notificationsTopicName;
		this.notificationsErrorChannel = notificationsErrorChannel;
	}

	public long publish(Notification notification) {
		String messageContent = serialize(notification);
		LOG.info("Sending {}", messageContent);
		
		var message = new ProducerRecord<String, String>(notificationsTopicName, notification.getId().toString(), messageContent);
		message.headers()
			.add(MessageHeaders.ERROR_CHANNEL, notificationsErrorChannel.getBytes());
		
		// return publishInTransaction(message);
		return publishOutOfTransaction(message);
	}

	private long publishOutOfTransaction(ProducerRecord<String, String> message) {
		try {
			var result = kafkaTemplate.send(message).get();
			return result.getRecordMetadata().offset();
		} catch (InterruptedException | ExecutionException ex) {
			LOG.error("Error while producing the message", ex);
			return -1;
		}
	}

	private long publishInTransaction(ProducerRecord<String, String> message) {
		return this.kafkaTemplate.executeInTransaction(kafkaTemplate -> {
			try {
				var result = kafkaTemplate.send(message).get();
				return result.getRecordMetadata().offset();
			} catch (InterruptedException | ExecutionException ex) {
				LOG.error("Error while producing the message", ex);
				return -1L;
			}
		});
	}

	private String serialize(Notification notification) {
		try {
			return objectMapper.writeValueAsString(notification);
		} catch (JsonProcessingException ex) {
			LOG.error("Error during message serialization.", ex);
			throw new RuntimeException(ex);
		}
	}
}
