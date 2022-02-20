package com.github.wesleyegberto.producer;

import java.util.function.Supplier;

import com.github.wesleyegberto.producer.model.SecurityEvent;
import com.github.wesleyegberto.producer.service.EventProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

@SpringBootApplication
public class ProducerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Bean
	public Supplier<Message<SecurityEvent>> securityEventSupplier(EventProducer eventProducer) {
		return eventProducer::createEvent;
	}

	@Bean
	public Supplier<Message<SecurityEvent>> securityEventByRegionSupplier(EventProducer eventProducer) {
		return eventProducer::createEventRegion;
	}

}

