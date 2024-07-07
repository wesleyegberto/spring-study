package com.wesleyegberto.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
public class OrderApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderApiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
