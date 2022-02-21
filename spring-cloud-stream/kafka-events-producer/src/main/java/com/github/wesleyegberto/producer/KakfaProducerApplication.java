package com.github.wesleyegberto.producer;

import java.util.function.Supplier;

import com.github.wesleyegberto.producer.model.Order;
import com.github.wesleyegberto.producer.service.EventProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

@SpringBootApplication
public class KakfaProducerApplication {
	public static void main(String[] args) {
		SpringApplication.run(KakfaProducerApplication.class, args);
	}

	@Bean
	public Supplier<Message<Order>> orderBuySupplier(EventProducer eventProducer) {
		return eventProducer::createBuyOrder;
	}

	@Bean
	public Supplier<Message<Order>> orderSellSupplier(EventProducer eventProducer) {
		return eventProducer::createSellOrder;
	}

}

