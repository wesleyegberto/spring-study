package com.github.wesleyegberto.kafkaschemas.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

import io.confluent.kafka.schemaregistry.client.SchemaRegistryClientConfig;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

// @Configuration
public class KakfaConfiguration {
	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${spring.kafka.properties.schema.registry.url}")
	private String schemaRegistryUrl;

	@Value("${spring.kafka.properties.schema.registry.basic.auth.user.info}")
	private String schemaRegistryAuthUserInfo;

	@Value("${spring.kafka.consumer.group-id}")
	private String consumerGroupId;

	@Bean
	ConsumerFactory<String, Object> avroConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);

		// Deserializer config
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
		props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

		props.put(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, this.schemaRegistryUrl);
		props.put(SchemaRegistryClientConfig.USER_INFO_CONFIG, this.schemaRegistryAuthUserInfo);
		props.put(SchemaRegistryClientConfig.BASIC_AUTH_CREDENTIALS_SOURCE, "USER_INFO");

		props.put(ConsumerConfig.GROUP_ID_CONFIG, this.consumerGroupId);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		props.put("enable.auto.commit", false);

		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	ConcurrentKafkaListenerContainerFactory<String, GenericRecord> kafkaListenerContainerFactory(
			ConsumerFactory<String, Object> avroConsumerFactory) {
		var factory = new ConcurrentKafkaListenerContainerFactory<String, GenericRecord>();
		factory.setConsumerFactory(avroConsumerFactory);
		factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
		return factory;
	}

}
