package com.wesleyegberto.domain.movies;

import static java.util.stream.Collectors.toList;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String title;
	@Column(length = 2048)
	private String description;
	private String category;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<Person> starring;

	Movie() {
	}

	public Movie(String title, String description, String category, List<String> starring) {
		this.title = title;
		this.description = description;
		this.category = category;
		this.starring = starring.stream()
				.map(Person::new)
				.collect(toList());
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

	public List<Person> getStarring() {
		return starring;
	}
}
