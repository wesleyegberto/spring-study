package com.github.wesleyegberto.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.github.wesleyegberto.entity.Pet;
import com.github.wesleyegberto.entity.PetNotFoundException;
import com.github.wesleyegberto.repository.PetsRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

// Without a real webserver
@AutoConfigureJsonTesters
@WebMvcTest(PetsController.class) // will create any deps or features needed
public class PetsControllerMockMvcWithContextTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private PetsRepository petsRepository;

	// This object will be initialized thanks to @AutoConfigureJsonTesters
	@Autowired
	private JacksonTester<Pet> json;

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
