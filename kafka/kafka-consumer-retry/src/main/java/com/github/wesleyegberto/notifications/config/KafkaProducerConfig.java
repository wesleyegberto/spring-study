package com.github.wesleyegberto.notifications.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfig {
    @Value("${spring.application.name}")
    private String applicationName;
    
    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBootstrapAddress;
    
    @Value("${kafka.notifications.topic.name}")
    private String notificationTopicName;
    @Value("${kafka.notifications.error-channel}")
    private String notificationErrorChannel;
    
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
        return new KafkaAdmin(configs);
    }
     
    @Bean
    public List<NewTopic> newTopic() {
		return List.of(
			new NewTopic(notificationTopicName, 5, (short) 5),
			new NewTopic(notificationErrorChannel, 1, (short) 1)
		);
    }
    
    @Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
		configProps.put(ProducerConfig.CLIENT_ID_CONFIG, this.applicationName);
		// configProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "tx.notifications.producer");
		
		configProps.put(ProducerConfig.ACKS_CONFIG, "all"); // ACK of ISR brokers
		configProps.put(ProducerConfig.RETRIES_CONFIG, 3); // will retry 3 times
		configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1_000); // will retry after 1s
		configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1); // to guarantee ordering when retry is enable but lower throughput
		
		// Setup Confluent interceptor
		configProps.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, "io.confluent.monitoring.clients.interceptor.MonitoringProducerInterceptor");
		return new DefaultKafkaProducerFactory<>(configProps);
	}
    
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate(ProducerFactory<String, String> producerFactory) {
		return new KafkaTemplate<>(producerFactory);
	}
}