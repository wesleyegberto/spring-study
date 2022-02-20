package com.github.wesleyegberto.producer;

import java.util.function.Consumer;
import java.util.function.Function;

import com.github.wesleyegberto.producer.model.SecurityEvent;
import com.github.wesleyegberto.producer.service.EventConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@Bean
	public Consumer<SecurityEvent> loggerConsumer(EventConsumer eventConsumer) {
		return eventConsumer::logEvent;
	}

	@Bean
	public Function<SecurityEvent, SecurityEvent> securityEventProcessor(EventConsumer eventConsumer) {
		// the returning will be published to the `out` destination binding
		return eventConsumer::processEvent;
	}
}

