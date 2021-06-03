package com.github.wesleyegberto.springmongodb.repository;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.wesleyegberto.springmongodb.entity.Group;

@Repository
public interface GroupRepository<T> extends MongoRepository<Group<T>, UUID> {
}
