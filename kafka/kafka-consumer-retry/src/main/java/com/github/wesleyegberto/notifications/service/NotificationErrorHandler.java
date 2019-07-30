package com.github.wesleyegberto.notifications.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class NotificationErrorHandler implements ErrorHandler, KafkaListenerErrorHandler {
	private static final Logger LOG = LoggerFactory.getLogger(NotificationErrorHandler.class);

	@Override
	public void handle(Exception thrownException, ConsumerRecord<?, ?> data) {
		LOG.warn("[ErrorHandler] Handling error: {}", thrownException.getMessage());
	}

	@Override
	public Object handleError(Message<?> message, ListenerExecutionFailedException thrownException) {
		LOG.warn("[KafkaListener] Handling error: {}", thrownException.getMessage());
		return null;
	}
}
