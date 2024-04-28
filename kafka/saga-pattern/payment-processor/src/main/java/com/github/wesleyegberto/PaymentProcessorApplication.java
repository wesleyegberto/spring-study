package com.github.wesleyegberto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class PaymentProcessorApplication {
	public static void main(String[] args) {
		SpringApplication.run(PaymentProcessorApplication.class, args);
	}
}
