package com.github.wesleyegberto.kafkaschemas.consumer;

import java.time.Duration;

import com.github.wesleyegberto.kafkaavro.models.PersonMessage;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class PersonRegisteredListener {
	private static final Logger LOG = LoggerFactory.getLogger(PersonRegisteredListener.class);

	@KafkaListener(topics = "${spring.kafka.person-registration.topic.name}")
	public void onMessage(ConsumerRecord<String, PersonMessage> consumerRecord, Acknowledgment ack) {
		if (consumerRecord == null || consumerRecord.value() == null) {
			LOG.error("Invalid message");
			ack.acknowledge();
			return;
		}
		LOG.info("Received a message: {}", consumerRecord.value());

		try {
			ack.acknowledge();
		} catch (RuntimeException ex) {
			LOG.error("Error during processing: {}", ex.getMessage());
			ack.nack(Duration.ofSeconds(1));
		}
	}
}
