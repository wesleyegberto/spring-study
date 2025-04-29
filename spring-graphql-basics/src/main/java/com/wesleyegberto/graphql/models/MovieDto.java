package com.wesleyegberto.graphql.models;

import static java.util.stream.Collectors.toList;

import java.util.List;

import com.wesleyegberto.domain.movies.Movie;

public class MovieDto {
	private int id;
	private String title;
	private String description;
	private String category;
	private List<PersonDto> starring;

	public MovieDto(int id, String title, String description, String category, List<PersonDto> starring) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.category = category;
		this.starring = starring;
	}

	public static MovieDto from(Movie movie) {
		var starring = movie.getStarring().stream().map(PersonDto::from).collect(toList());
		return new MovieDto(movie.getId(), movie.getTitle(), movie.getDescription(), movie.getCategory(), starring);
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public List<PersonDto> getStarring() {
		return starring;
	}
}
