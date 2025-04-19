package com.github.wesleyegberto.kafkaschemas.producer;

import java.util.concurrent.ExecutionException;

import com.github.wesleyegberto.kafkaavro.models.AddressMessage;
import com.github.wesleyegberto.kafkaavro.models.PersonMessage;
import com.github.wesleyegberto.kafkaschemas.people.Person;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PersonRegisteredProducer {
	private final KafkaTemplate<String, PersonMessage> kafkaTemplate;
	private final String topicName;

	PersonRegisteredProducer(KafkaTemplate<String, PersonMessage> KafkaTemplate,
			@Value("${spring.kafka.person-registration.topic.name}") String topicName) {
		this.kafkaTemplate = KafkaTemplate;
		this.topicName = topicName;
	}

	public void notifyRegistration(Person person) {
		var payload = buildMessage(person);
		var record = new ProducerRecord<String, PersonMessage>(topicName, payload);
		try {
			this.kafkaTemplate.send(record).get();

		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	private PersonMessage buildMessage(Person person) {
		var builder = PersonMessage.newBuilder()
				.setName(person.getName());

		if (person.getBirthDate() != null) {
			builder.setBirthDate(person.getBirthDate().toString());
		}

		if (person.getEmails() != null) {
			builder.setEmails(person.getEmails());
		}

		if (person.getAddress() != null) {
			var addressBuilder = AddressMessage.newBuilder()
					.setPostalCode(person.getAddress().getPostalCode())
					.setStreet(person.getAddress().getStreet())
					.setActive(person.getAddress().isActive());
			builder.setAddress(addressBuilder.build());
		}

		return builder.build();
	}
}
