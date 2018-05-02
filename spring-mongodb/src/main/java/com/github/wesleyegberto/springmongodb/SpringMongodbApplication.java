package com.github.wesleyegberto.springmongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class SpringMongodbApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringMongodbApplication.class, args);
	}
}
