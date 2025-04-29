package com.wesleyegberto;

import java.util.List;
import java.util.stream.Stream;

import com.wesleyegberto.domain.movies.Movie;
import com.wesleyegberto.domain.movies.MovieRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class GraphqlStartApplication {
	public static void main(String[] args) {
		SpringApplication.run(GraphqlStartApplication.class, args);
	}
}

@Component
class MoviesSeed implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(MoviesSeed.class);

	private final MovieRepository movieRepository;

	public MoviesSeed(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		LOG.info("Inserting movies");
		Stream.of(
				new Movie("The Matrix",
						"Thomas A. Anderson is a man living two lives. By day he is an average computer programmer and by night a hacker known as Neo. Neo has always questioned his reality, but the truth is far beyond his imagination. Neo finds himself targeted by the police when he is contacted by Morpheus, a legendary computer hacker branded a terrorist by the government. As a rebel against the machines, Neo must confront the agents: super-powerful computer programs devoted to stopping Neo and the entire human rebellion.",
						"Action, Sci-Fi",
						List.of("Keanu Reeves", "Laurence Fishburne", "Carrie-Annie Moss", "Hugo Weaving")),
				new Movie("The Lord of the Rings: The Fellowship of the Ring",
						"An ancient Ring thought lost for centuries has been found, and through a strange twist of fate has been given to a small Hobbit named Frodo. When Gandalf discovers the Ring is in fact the One Ring of the Dark Lord Sauron, Frodo must make an epic quest to Mount Doom in order to destroy it. However, he does not go alone. He is joined by Gandalf, Legolas the elf, Gimli the Dwarf, Aragorn, Boromir, and his three Hobbit friends Merry, Pippin, and Samwise. Through mountains, snow, darkness, forests, rivers and plains, facing evil and danger at every corner the Fellowship of the Ring must go. Their quest to destroy the One Ring is the only hope for the end of the Dark Lords reign.",
						"Adventure, Drama, Fantasy",
						List.of("Elijah Wood", "Ian McKellen", "Orlando Boom", "Viggo Mortesen", "Andy Serkis")),
				new Movie("The Dark Knight",
						"Set within a year after the events of Batman Begins (2005), Batman, Lieutenant James Gordon, and new District Attorney Harvey Dent successfully begin to round up the criminals that plague Gotham City, until a mysterious and sadistic criminal mastermind known only as The Joker appears in Gotham, creating a new wave of chaos. Batman's struggle against The Joker becomes deeply personal, forcing him to confront everything he believes and improve his technology to stop him. A love triangle develops between Bruce Wayne, Dent, and Rachel Dawes.",
						"Action, Crime, Drama, Thriller",
						List.of("Christian Bale", "Heath Ledger", "Aaron Eckhart", "Michael Caine", "Gary Oldman"))
				).forEach(movieRepository::save);
	}
}
