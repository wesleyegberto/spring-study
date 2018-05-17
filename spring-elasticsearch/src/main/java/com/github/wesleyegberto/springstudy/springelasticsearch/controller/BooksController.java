package com.github.wesleyegberto.springstudy.springelasticsearch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wesleyegberto.springstudy.springelasticsearch.entity.Book;
import com.github.wesleyegberto.springstudy.springelasticsearch.repository.BookRepository;

@RestController
@RequestMapping("/books")
public class BooksController {

	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping
	public Iterable<Book> getAll() {
		return bookRepository.findAll();
	}
	
	@PostMapping
	public void save(@RequestBody Book book) {
		bookRepository.save(book);
	}
}
