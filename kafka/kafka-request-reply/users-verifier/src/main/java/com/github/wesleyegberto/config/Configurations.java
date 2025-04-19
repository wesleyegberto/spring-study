package com.github.wesleyegberto.config;

import com.github.wesleyegberto.usersverifier.verification.UserVerificationRequest;
import com.github.wesleyegberto.usersverifier.verification.UserVerificationResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

public class Configurations {
	@Bean
	KafkaTemplate<String, UserVerificationResponse> kafkaTemplate(
			ProducerFactory<String, UserVerificationResponse> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}

	@Bean
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, UserVerificationRequest>> kafkaListenerContainerFactory(
			ConsumerFactory<String, UserVerificationRequest> consumerFactory,
			KafkaTemplate<String, UserVerificationResponse> kafkaTemplate) {
		var factory = new ConcurrentKafkaListenerContainerFactory<String, UserVerificationRequest>();
		factory.setConsumerFactory(consumerFactory);
		factory.setReplyTemplate(kafkaTemplate);
		return factory;
	}
}
