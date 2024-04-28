package com.github.wesleyegberto.kafkastreams;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

@Configuration
@EnableKafka
@EnableKafkaStreams // configure and create KafkaStreams client to manager lifecycle
public class KafkaConfigurator {
	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaBootstrapServers;

	@Value("${spring.kafka.streams.words.topic.name}")
	private String wordsStreamTopicName;
	@Value("${spring.kafka.streams.words-count.topic.name}")
	private String wordsCountsStreamTopicName;

	@Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
	KafkaStreamsConfiguration kafkaStreamsConfiguration() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(StreamsConfig.APPLICATION_ID_CONFIG, "words-processor");
		configs.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
		configs.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		configs.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
		return new KafkaStreamsConfiguration(configs);
	}
}
