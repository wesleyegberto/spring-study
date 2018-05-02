package com.github.wesleyegberto.reactiveweb.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.github.wesleyegberto.reactiveweb.entity.Person;

import reactor.core.publisher.Flux;

@Repository
public interface PersonRepository extends ReactiveMongoRepository<Person, UUID> {
	Flux<Person> findByName(String name);
}
