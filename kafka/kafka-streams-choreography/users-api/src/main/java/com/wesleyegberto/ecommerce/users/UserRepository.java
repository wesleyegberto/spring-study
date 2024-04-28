package com.wesleyegberto.ecommerce.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	boolean existsByTaxId(String taxId);
}
