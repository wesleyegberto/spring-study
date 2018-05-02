package com.github.wesleyegberto.springcontextxml.repository;

import org.springframework.stereotype.Repository;

import com.github.wesleyegberto.springcontextxml.model.Customer;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

	@Override
	public Customer getById(Long id) {
		return new Customer(id, "John Doe");
	}

}
