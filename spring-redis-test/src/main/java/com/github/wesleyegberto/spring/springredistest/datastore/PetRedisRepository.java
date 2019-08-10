package com.github.wesleyegberto.spring.springredistest.datastore;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRedisRepository extends CrudRepository<Pet, Long> {
}
