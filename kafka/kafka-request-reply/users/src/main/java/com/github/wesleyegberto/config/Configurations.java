package com.github.wesleyegberto.config;

import java.time.Duration;

import com.github.wesleyegberto.users.verification.UserVerificationRequest;
import com.github.wesleyegberto.users.verification.UserVerificationResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
public class Configurations {
	@Value("${kafka.reply-topic}")
	private String replyTopic;
	@Value("${kafka.reply-timeout}")
	private int replyTimeout;

	@Bean
	KafkaTemplate<String, UserVerificationRequest> kafkaTemplate(
			ProducerFactory<String, UserVerificationRequest> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}

	@Bean
	KafkaMessageListenerContainer<String, UserVerificationResponse> responseListenerContainer(
			ConsumerFactory<String, UserVerificationResponse> consumerFactory) {
		ContainerProperties containerProperties = new ContainerProperties(replyTopic);
		return new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
	}

	@Bean
	ReplyingKafkaTemplate<String, UserVerificationRequest, UserVerificationResponse> replyingKafkaTemplate(
			ProducerFactory<String, UserVerificationRequest> producerFactory,
			KafkaMessageListenerContainer<String, UserVerificationResponse> responseListenerContainer) {
		var replyingKafkaTemplate = new ReplyingKafkaTemplate<>(producerFactory, responseListenerContainer);
		replyingKafkaTemplate.setDefaultReplyTimeout(Duration.ofSeconds(replyTimeout));
		return replyingKafkaTemplate;
	}
}
