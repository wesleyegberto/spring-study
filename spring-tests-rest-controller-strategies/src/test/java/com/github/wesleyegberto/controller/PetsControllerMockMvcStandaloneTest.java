package com.github.wesleyegberto.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.entity.Pet;
import com.github.wesleyegberto.entity.PetNotFoundException;
import com.github.wesleyegberto.repository.PetsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// Without a real webserver
@ExtendWith(MockitoExtension.class)
public class PetsControllerMockMvcStandaloneTest {
	private MockMvc mvc;

	@Mock
	private PetsRepository petsRepository;

	@InjectMocks
	private PetsController petsController;

	private JacksonTester<Pet> json;

	@BeforeEach
	public void setup() {
		// if using JUnit 4
		// MockitoAnnotations.initMocks(this);

		// we can't use @AutoConfigureJsonTesters (missing Spring context)
		JacksonTester.initFields(this, new ObjectMapper());

		mvc = MockMvcBuilders.standaloneSetup(petsController)
			.setControllerAdvice(new PetExceptionHandler())
			.addFilters(new ApiVersionFilter())
			.build();
	}

	@Test
	public void should_return_existing_pet() throws Exception {
		given(petsRepository.findById(42))
				.willReturn(Optional.of(new Pet(42, "Marley", "Wesley")));

		MockHttpServletResponse response = mvc.perform(
					get("/pets/42").accept(MediaType.APPLICATION_JSON)
				)
				.andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getContentAsString())
			.isEqualTo(
				json.write(new Pet(42, "Marley", "Wesley")).getJson()
			);
	}

	@Test
	public void should_return_not_found_for_non_existing_pet() throws Exception {
		given(petsRepository.findById(42))
				.willThrow(new PetNotFoundException());

		MockHttpServletResponse response = mvc.perform(
					get("/pets/42").accept(MediaType.APPLICATION_JSON)
				)
				.andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		assertThat(response.getContentAsString()).isEmpty();
	}

	@Test
	public void should_create_new_pet() throws Exception {
		MockHttpServletResponse response = mvc.perform(
					post("/pets")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(
							json.write(new Pet("Marley", "Wesley")).getJson()
						)
				)
				.andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

		ArgumentCaptor<Pet> argCaptor = ArgumentCaptor.forClass(Pet.class);
		verify(petsRepository).save(argCaptor.capture());
		Pet pet = argCaptor.getValue();

		assertThat(pet.getId()).isEqualTo(0);
		assertThat(pet.getName()).isEqualTo("Marley");
		assertThat(pet.getOwner()).isEqualTo("Wesley");
	}

	@Test
	public void should_add_api_version_header() throws Exception {
		MockHttpServletResponse response = mvc.perform(
					get("/pets/42").accept(MediaType.APPLICATION_JSON)
				)
				.andReturn()
				.getResponse();

		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.getHeaders("X-PETS-VERSION")).containsOnly("v1");
	}
}
