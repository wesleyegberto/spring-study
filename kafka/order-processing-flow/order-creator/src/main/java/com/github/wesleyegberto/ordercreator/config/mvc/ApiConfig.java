package com.github.wesleyegberto.ordercreator.config.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class ApiConfig {
	@Bean
	public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
		return Jackson2ObjectMapperBuilder.json()
			.simpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
			.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
			.serializationInclusion(Include.NON_NULL);
	}
}
