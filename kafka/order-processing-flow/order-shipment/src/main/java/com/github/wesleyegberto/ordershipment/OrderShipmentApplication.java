package com.github.wesleyegberto.ordershipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class OrderShipmentApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderShipmentApplication.class, args);
	}
}
