package com.wesleyegberto.usersauditor.auditing;

import java.time.Duration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wesleyegberto.usersauditor.history.UserDataHistoryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class UserAuditorKafkaListener {
	private static final Logger LOG = LoggerFactory.getLogger(UserAuditorKafkaListener.class);

	private final ObjectMapper objectMapper;
	private final UserDataHistoryService historyService;

	public UserAuditorKafkaListener(ObjectMapper objectMapper, UserDataHistoryService historyService) {
		this.objectMapper = objectMapper;
		this.historyService = historyService;
	}

	@KafkaListener(
			topics = "${kafka.users-auditing.topic.name}",
			containerFactory = "usersAuditingListenerContainerFactory"
	)
	public void onMessage(Message<String> kafkaMessage, Acknowledgment ack) {
		var message = kafkaMessage.getPayload();
		LOG.info("Message received: {}", message);

		try {
			var userEvent = objectMapper.readValue(message, UserEvent.class);
			historyService.save(userEvent);
			ack.acknowledge();
		} catch (JsonProcessingException e) {
			LOG.error("Error at deserialization: {}", e.getMessage());
			ack.nack(Duration.ofSeconds(1));
		}
	}
}
