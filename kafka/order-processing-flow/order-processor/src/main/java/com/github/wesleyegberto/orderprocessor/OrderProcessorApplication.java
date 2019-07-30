package com.github.wesleyegberto.orderprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka // enable kafka listener
public class OrderProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessorApplication.class, args);
	}

}
