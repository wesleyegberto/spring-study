package com.wesleyegberto.ecommerce.users;

import com.wesleyegberto.ecommerce.orders.management.Client;

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

	public Client findByTaxId(String taxId) {
		try {
			return restTemplate.getForObject(usersApiUrl + "/users/search?taxId={taxId}", Client.class, taxId);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode().is4xxClientError()) {
				throw new ClientNotFoundException("Client not found by: " + taxId);
			}
			throw new RuntimeException("Error during client query: " + ex.getMessage());
		} catch (RuntimeException ex) {
			throw new RuntimeException("Error during client query: " + ex.getMessage());
		}
	}
}
