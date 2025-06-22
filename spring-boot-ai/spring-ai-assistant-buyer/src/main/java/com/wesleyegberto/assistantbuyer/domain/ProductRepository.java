package com.wesleyegberto.assistantbuyer.domain;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Integer> {
	List<Product> findByCategory(String category);
}
