package com.github.wesleyegberto.reactiveweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class SpringReactiveWebApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveWebApplication.class, args);
	}
}
