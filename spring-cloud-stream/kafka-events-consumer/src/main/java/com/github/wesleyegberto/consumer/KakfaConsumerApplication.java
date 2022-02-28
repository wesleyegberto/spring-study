package com.github.wesleyegberto.consumer;

import com.github.wesleyegberto.consumer.model.Order;
import com.github.wesleyegberto.consumer.service.EventConsumer;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class KakfaConsumerApplication {
	public static void main(String[] args) {
		SpringApplication.run(KakfaConsumerApplication.class, args);
	}

	@Bean
	public Consumer<Order> buyOrderLogger(EventConsumer eventConsumer) {
		return eventConsumer::logBuyOrder;
	}

	@Bean
	public Consumer<Order> sellOrderLogger(EventConsumer eventConsumer) {
		return eventConsumer::logSellOrder;
	}
}

