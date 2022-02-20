package com.github.wesleyegberto.sampleapp;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaRepositories
public class SampleAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(SampleAppApplication.class, args);
	}
}

@RestController
@RequestMapping("/books")
class BooksController {
	private BookRepository bookRepository;

	public BooksController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Book> findAll() {
		return this.bookRepository.findAll();
	}
}

@Repository
interface BookRepository extends JpaRepository<Book, Long> {
}

@Entity
@Table(name = "Books")
class Book {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String author;

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}
}