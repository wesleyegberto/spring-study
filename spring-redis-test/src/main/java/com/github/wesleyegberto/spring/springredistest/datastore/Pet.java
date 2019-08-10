package com.github.wesleyegberto.spring.springredistest.datastore;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("pet")
public class Pet {
	@Id
	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
