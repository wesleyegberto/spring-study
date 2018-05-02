package com.github.wesleyegberto.springcontextxml.repository;

import com.github.wesleyegberto.springcontextxml.model.Customer;

public interface CustomerRepository {

	Customer getById(Long id);

}
