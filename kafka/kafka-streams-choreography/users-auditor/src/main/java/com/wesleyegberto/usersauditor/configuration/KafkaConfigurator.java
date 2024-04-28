package com.wesleyegberto.usersauditor.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.IsolationLevel;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

@Configuration
public class KafkaConfigurator {
	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaBootstrapAddress;

	@Value("${kafka.users-auditing.topic.name}")
	private String usersAuditingTopicName;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic usersAuditingTopic() {
		return new NewTopic(usersAuditingTopicName, 1, (short) 1);
	}

	@Bean
	public ConsumerFactory<String, String> usersAuditingConsumerFactory() {
		var props = Map.<String, Object>of(
				ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.kafkaBootstrapAddress,
				ConsumerConfig.CLIENT_ID_CONFIG, "users-auditing",
				ConsumerConfig.GROUP_ID_CONFIG, "users-auditing",
				ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
				ConsumerConfig.ISOLATION_LEVEL_CONFIG, IsolationLevel.READ_COMMITTED.toString().toLowerCase(),
				ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10_000,
				ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3_000,
				ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false,
				ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 2_000L);
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> usersAuditingListenerContainerFactory(
			ConsumerFactory<String, String> usersAuditingConsumerFactory) {
		var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(usersAuditingConsumerFactory);
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		return factory;
	}
}
