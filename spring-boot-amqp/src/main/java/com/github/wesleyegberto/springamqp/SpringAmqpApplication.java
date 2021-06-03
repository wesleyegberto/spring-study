package com.github.wesleyegberto.springamqp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class SpringAmqpApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringAmqpApplication.class, args);
	}
}
