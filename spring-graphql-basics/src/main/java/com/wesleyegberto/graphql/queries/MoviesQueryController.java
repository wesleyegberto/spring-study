package com.wesleyegberto.graphql.queries;

import java.util.List;
import java.util.stream.Collectors;

import com.wesleyegberto.domain.movies.Movie;
import com.wesleyegberto.domain.movies.MovieRepository;
import com.wesleyegberto.graphql.models.MovieDto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MoviesQueryController {
	private final MovieRepository movieRepository;

	public MoviesQueryController(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@QueryMapping
	public List<MovieDto> recentsMovies() {
		return movieRepository.top5Recents()
				.stream()
				.map(MovieDto::from)
				.collect(Collectors.toList());
	}

	@QueryMapping
	public List<MovieDto> searchMovies(@Argument String title, @Argument String category, @Argument Integer page, @Argument Integer pageSize) {
		Pageable paging = PageRequest.of(page, pageSize);

		List<Movie> searchResult;
		if (title != null && !title.isBlank()) {
			searchResult = movieRepository.findByTitleContainingIgnoreCase(title, paging);
		} else if (category != null && !category.isBlank()) {
			searchResult = movieRepository.findByCategoryContainingIgnoreCase(category, paging);
		} else {
			throw new IllegalArgumentException("Inform title or category to search");
		}

		return searchResult
				.stream()
				.map(MovieDto::from)
				.collect(Collectors.toList());
	}
}
