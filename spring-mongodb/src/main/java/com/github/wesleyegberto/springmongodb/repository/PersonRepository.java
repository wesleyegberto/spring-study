package com.github.wesleyegberto.springmongodb.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.wesleyegberto.springmongodb.entity.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, UUID> {
	List<Person> findByName(String name);
}
