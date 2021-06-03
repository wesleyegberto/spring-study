package com.github.wesleyegberto.springcontextxml.repository;

import com.github.wesleyegberto.springcontextxml.model.Customer;

public class InMemoryCustomerRepository implements CustomerRepository {

	@Override
	public Customer getById(Long id) {
		return new Customer(id, "John Doe");
	}

}
