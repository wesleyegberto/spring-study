package com.wesleyegberto.ecommerce.users;

import com.wesleyegberto.ecommerce.orders.management.Customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Repository
public class UserRepository {
	private final String usersApiUrl;
	private final RestTemplate restTemplate;

	UserRepository(@Value("${users-api.url}") String usersApiUrl, RestTemplate restTemplate) {
		this.usersApiUrl = usersApiUrl;
		this.restTemplate = restTemplate;
	}

	public Customer findByTaxId(String taxId) {
		try {
			return restTemplate.getForObject(usersApiUrl + "/users/search?taxId={taxId}", Customer.class, taxId);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().is4xxClientError()) {
				throw new CustomerNotFoundException("Customer not found by: " + taxId);
			}
			throw new RuntimeException("Error during customer query: " + ex.getMessage());
		} catch (RuntimeException ex) {
			throw new RuntimeException("Error during customer query: " + ex.getMessage());
		}
	}

	public CustomerHistoricInformation getHistoricByCustomerId(Long customerId) {
		try {
			return restTemplate.getForObject(usersApiUrl + "/users/{id}/historic", CustomerHistoricInformation.class, customerId);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().is4xxClientError()) {
				throw new CustomerNotFoundException("Customer not found by: " + customerId);
			}
			throw new RuntimeException("Error during customer query: " + ex.getMessage());
		} catch (RuntimeException ex) {
			throw new RuntimeException("Error during customer query: " + ex.getMessage());
		}
	}
}

