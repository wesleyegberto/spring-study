package com.github.wesleyegberto.springcontextxml.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wesleyegberto.springcontextxml.model.Customer;
import com.github.wesleyegberto.springcontextxml.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	private CustomerRepository customerRepository;
	
	CustomerServiceImpl() {
	}
	
	@Autowired
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
