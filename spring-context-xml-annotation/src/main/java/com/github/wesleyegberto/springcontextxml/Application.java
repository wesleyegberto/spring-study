package com.github.wesleyegberto.springcontextxml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.wesleyegberto.springcontextxml.model.Customer;
import com.github.wesleyegberto.springcontextxml.service.CustomerService;

public class Application {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
		
		CustomerService customerService = context.getBean(CustomerService.class);
		
		Customer customer = customerService.getCustomerById(13L);
		System.out.println("Customer: " + customer.getName());
	}
}
