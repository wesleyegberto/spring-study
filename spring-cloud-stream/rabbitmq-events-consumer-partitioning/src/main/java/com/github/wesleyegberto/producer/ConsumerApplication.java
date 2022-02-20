package com.github.wesleyegberto.producer;

import java.util.function.Consumer;

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
	public Consumer<SecurityEvent> partitionedConsumer(EventConsumer eventConsumer) {
		return eventConsumer::consumeEvent;
	}
}

