package com.github.wesleyegberto.consumer;

import java.util.Map;
import java.util.function.Function;

import com.github.wesleyegberto.consumer.model.SecurityEvent;
import com.github.wesleyegberto.consumer.service.EventConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@Bean
	public Function<SecurityEvent, Map<String, String>> securityEventConsumer(EventConsumer eventConsumer) {
		return eventConsumer::consumeEvent;
	}
}

