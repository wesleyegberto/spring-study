package com.wesleyegberto.graphql.mutations;

import com.wesleyegberto.domain.movies.MovieRepository;
import com.wesleyegberto.graphql.models.MovieDto;
import com.wesleyegberto.graphql.models.MovieRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MoviesMutationController {
	private static final Logger LOG = LoggerFactory.getLogger(MoviesMutationController.class);

	private final MovieRepository movieRepository;

	MoviesMutationController(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@MutationMapping
	public MovieDto addMovieVerbose(@Arguments MovieRequest movie) {
		return saveMovie(movie);
	}

	@MutationMapping
	public MovieDto addMovie(@Argument MovieRequest movie) {
		return saveMovie(movie);
	}

	private MovieDto saveMovie(MovieRequest movie) {
		LOG.info("Saving movie: {}", movie);
		var savedMovie = movieRepository.save(movie.toEntity());
		return MovieDto.from(savedMovie);
	}
}
