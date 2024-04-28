package com.wesleyegberto.ecommerce.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserEventPublisher {
	private static final Logger LOG = LoggerFactory.getLogger(UserEventPublisher.class);

	private final String usersAuditingTopicName;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public UserEventPublisher(@Value("${kafka.users-auditing.topic.name}") String usersAuditingTopicName,
			KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.usersAuditingTopicName = usersAuditingTopicName;
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	public void publish(UserEvent userEvent) {
		var message = createMessage(userEvent);
		LOG.info("Sending event {} - {}", userEvent.eventId(), message);
		this.kafkaTemplate.send(message);
	}

	private ProducerRecord<String, String> createMessage(UserEvent userEvent) {
		var messageContent = serialize(userEvent);
		return new ProducerRecord<String, String>(this.usersAuditingTopicName,
				userEvent.eventId().toString(), messageContent);
	}

	private String serialize(UserEvent userEvent) {
		try {
			return objectMapper.writeValueAsString(userEvent);
		} catch (JsonProcessingException ex) {
			throw new RuntimeException(ex);
		}
	}
}
