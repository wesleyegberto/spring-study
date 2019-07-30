package com.github.wesleyegberto.notifications.config;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.requests.IsolationLevel;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapAddress;
    
    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

	@Bean("notificationsConsumerFactory")
	public ConsumerFactory<String, String> notificationsConsumerFactory() {
		var props = new HashMap<String, Object>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, this.consumerGroupId);
		props.put(ConsumerConfig.CLIENT_ID_CONFIG, "notificator-consumer");
		props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, IsolationLevel.READ_COMMITTED.toString().toLowerCase());
		
		// Amount of time the consumer can be out of touch without been considered off
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10_000);
		props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3_000); // session.timeout.ms / 3
		
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 2000L);
		
		// Setup Confluent interceptor
		props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, "io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor");
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean("kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(ConsumerFactory<String, String> notificationsConsumerFactory) {
		var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(notificationsConsumerFactory);
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		// factory.setErrorHandler(errorHandler);
		return factory;
	}

	@Bean("notificationsErrorConsumerFactory")
	public ConsumerFactory<String, String> notificationsErrorConsumerFactory() {
		var props = new HashMap<String, Object>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, this.consumerGroupId);
		props.put(ConsumerConfig.CLIENT_ID_CONFIG, "notification-error-consumer");
		
		// Amount of time the consumer can be out of touch without been considered off
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10_000);
		props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3_000); // session.timeout.ms / 3
		
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		props.put(ConsumerConfig.RETRY_BACKOFF_MS_CONFIG, 2000L);
		
		// Setup Confluent interceptor
		props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, "io.confluent.monitoring.clients.interceptor.MonitoringConsumerInterceptor");
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean("kafkaErrorListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaErrorListenerContainerFactory(ConsumerFactory<String, String> notificationsErrorConsumerFactory) {
		var factory = new ConcurrentKafkaListenerContainerFactory<String, String>();
		factory.setConsumerFactory(notificationsErrorConsumerFactory);
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		return factory;
	}
}
