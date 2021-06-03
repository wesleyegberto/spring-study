package com.github.wesleyegberto.springmongodb.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.github.wesleyegberto.springmongodb.entity.Person;

@Repository
public class ManualPersonRepository {
	private final MongoOperations operations;

	@Autowired
	public ManualPersonRepository(MongoOperations operations) {
		this.operations = operations;
	}
	
	public List<Person> findAll(String name) {
		if (name != null && !name.isEmpty()) {
			Query query = query(where("name").regex(name));
			return operations.find(query, Person.class);
		}
		return operations.findAll(Person.class);
	}
	
	public Optional<Person> findById(UUID id) {
		Query query = query(where("id").is(id));
		return Optional.ofNullable(operations.findOne(query, Person.class));
	}
	
	public Person save(Person person) {
		if (person.getId() == null)
			person.setId(UUID.randomUUID());
		operations.save(person);
		return person;
	}
}
