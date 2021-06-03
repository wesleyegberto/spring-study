package com.github.wesleyegberto.springcontextxml.service;

import com.github.wesleyegberto.springcontextxml.model.Customer;
import com.github.wesleyegberto.springcontextxml.repository.CustomerRepository;

public class CustomerServiceImpl implements CustomerService {
	private CustomerRepository customerRepository;
	
	CustomerServiceImpl() {
	}
	
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
	public Customer getCustomerById(Long id) {
		return customerRepository.getById(id);
	}
}
