package com.github.wesleyegberto.springstudy.springelasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "entities", type = "books")
public class Book {
	@Id
	private String id;
	
	private String name;
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
