package com.wesleyegberto.ecommerce.users;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	boolean existsByTaxId(String taxId);
	Optional<User> findByTaxId(String taxId);
}
