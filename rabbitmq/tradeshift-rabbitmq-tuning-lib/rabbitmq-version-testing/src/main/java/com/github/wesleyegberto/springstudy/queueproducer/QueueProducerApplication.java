package com.github.wesleyegberto.springstudy.queueproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
// When using spring.rabbitmq.enable.custom.autoconfiguration=true this shouldn't be used
//@EnableRabbit
public class QueueProducerApplication {
	public static void main(String[] args) {
		SpringApplication.run(QueueProducerApplication.class, args);
	}
}
