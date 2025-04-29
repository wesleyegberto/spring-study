package com.wesleyegberto.graphql.models;

import java.util.List;

import com.wesleyegberto.domain.movies.Movie;

public class MovieRequest {
	private String title;
	private String description;
	private String category;
	private List<String> starring;

	public MovieRequest(String title, String description, String category, List<String> starring) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.starring = starring;
	}

	public Movie toEntity() {
		return new Movie(title, description, category, starring);
	}

	@Override
	public String toString() {
		return "MovieRequest [category=" + category + ", description=" + description + ", starring="
				+ starring + ", title=" + title + "]";
	}
}
