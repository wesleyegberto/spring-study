package com.github.wesleyegberto.springstudy.springelasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories
public class SpringElasticsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringElasticsearchApplication.class, args);
	}
}
