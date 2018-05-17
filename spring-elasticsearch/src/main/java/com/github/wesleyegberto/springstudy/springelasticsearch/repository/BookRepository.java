package com.github.wesleyegberto.springstudy.springelasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.github.wesleyegberto.springstudy.springelasticsearch.entity.Book;

@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String>{

}
