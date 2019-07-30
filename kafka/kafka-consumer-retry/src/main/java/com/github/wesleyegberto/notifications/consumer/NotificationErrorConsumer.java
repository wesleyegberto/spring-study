package com.github.wesleyegberto.notifications.consumer;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Component
public class NotificationErrorConsumer {
	private static final Logger LOG = LoggerFactory.getLogger(NotificationErrorConsumer.class);
	
	private KafkaTemplate<String, String> kafkaTemplate;
	private String notificationsTopicName;
	private String notificationsPoisonedChannel;

	@Autowired
	public NotificationErrorConsumer(KafkaTemplate<String, String> kafkaTemplate,
			@Value("${kafka.notifications.topic.name}") String notificationsTopicName,
			@Value("${kafka.notifications.poisoned-channel}") String notificationsPoisonedChannel) {
		this.kafkaTemplate = kafkaTemplate;
		this.notificationsTopicName = notificationsTopicName;
		this.notificationsPoisonedChannel = notificationsPoisonedChannel;
	}

	@KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.notifications.error-channel}",
		errorHandler = "notificationErrorHandler", containerFactory = "kafkaErrorListenerContainerFactory")
	public void onMessage(Message<String> kafkaMessage, Acknowledgment ack) {
		var message = kafkaMessage.getPayload();
		LOG.info("[DLQ] Message received: {}", message);
		
		var errorChannelBytes = (byte[]) kafkaMessage.getHeaders().getErrorChannel();
		if (errorChannelBytes != null) {
			String errorChannel = new String(errorChannelBytes);
			LOG.info("Error Channel: {}", errorChannel);
		}

		try {
			LOG.info("[DLQ] Send to main topic");
			publish(message);
		} catch (RuntimeException ex) {
			LOG.error("Error during processing of notification error {}: {}", message, ex.getMessage());
			throw ex;
		} finally {
			ack.acknowledge();
		}
	}

	public long publish(String messageContent) {
		LOG.info("Sending {}", messageContent);
		var message = new ProducerRecord<String, String>(notificationsTopicName, messageContent);
		message.headers()
			.add("retries_count", "1".getBytes()) // retries
			.add(MessageHeaders.ERROR_CHANNEL, notificationsPoisonedChannel.getBytes()); // if we get another error we will give up
		
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
}
