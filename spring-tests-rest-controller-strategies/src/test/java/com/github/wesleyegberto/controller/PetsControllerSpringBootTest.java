package com.github.wesleyegberto.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import com.github.wesleyegberto.entity.Pet;
import com.github.wesleyegberto.entity.PetNotFoundException;
import com.github.wesleyegberto.repository.PetsRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

// a real webserver is started
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class PetsControllerSpringBootTest {
	@MockBean
	private PetsRepository petsRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void should_return_existing_pet() throws Exception {
		given(petsRepository.findById(42))
				.willReturn(Optional.of(new Pet(42, "Marley", "Wesley")));

		ResponseEntity<Pet> response = restTemplate.getForEntity("/pets/42", Pet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().equals(new Pet(42, "Marley", "Wesley")));
	}

	@Test
	public void should_return_not_found_for_non_existing_pet() throws Exception {
		given(petsRepository.findById(42))
				.willThrow(new PetNotFoundException());

		ResponseEntity<Pet> response = restTemplate.getForEntity("/pets/42", Pet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isNull();
	}

	@Test
	public void should_create_new_pet() throws Exception {
		ResponseEntity<Pet> response = restTemplate.postForEntity("/pets",
				new Pet("Marley", "Wesley"), Pet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		ArgumentCaptor<Pet> argCaptor = ArgumentCaptor.forClass(Pet.class);
		verify(petsRepository).save(argCaptor.capture());
		Pet pet = argCaptor.getValue();

		assertThat(pet.getId()).isEqualTo(0);
		assertThat(pet.getName()).isEqualTo("Marley");
		assertThat(pet.getOwner()).isEqualTo("Wesley");
	}

	@Test
	public void should_add_api_version_header() throws Exception {
		ResponseEntity<Pet> response = restTemplate.getForEntity("/pets/42", Pet.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getHeaders().get("X-PETS-VERSION")).containsOnly("v1");
	}
}
