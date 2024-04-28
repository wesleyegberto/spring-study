package com.github.wesleyegberto.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfigurator {
	@Bean
	public NewTopic orders() {
		return TopicBuilder.name("orders")
			.partitions(3)
			.compact()
			.build();
	}

	@Bean
	public NewTopic payment() {
		return TopicBuilder.name("payment-orders")
			.partitions(3)
			.compact()
			.build();
	}

	@Bean
	public NewTopic paymentConfirmation() {
		return TopicBuilder.name("stock-orders")
			.partitions(3)
			.compact()
			.build();
	}
}
