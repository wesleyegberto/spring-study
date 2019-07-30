package com.github.wesleyegberto.orderinvoicer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka // enable kafka listener
public class OrderInvoicerApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderInvoicerApplication.class, args);
	}
}
