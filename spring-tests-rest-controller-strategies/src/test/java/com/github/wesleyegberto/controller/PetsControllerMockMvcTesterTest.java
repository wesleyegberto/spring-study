package com.github.wesleyegberto.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.github.wesleyegberto.entity.Pet;
import com.github.wesleyegberto.entity.PetNotFoundException;
import com.github.wesleyegberto.repository.PetsRepository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

@WebMvcTest(PetsController.class) // will create any deps or features needed
public class PetsControllerMockMvcTesterTest {
	@Autowired
	private MockMvcTester mvc;

	@MockitoBean
	private PetsRepository petsRepository;

	@Test
	public void should_return_existing_pet() throws Exception {
		given(petsRepository.findById(42))
				.willReturn(Optional.of(new Pet(42, "Marley", "Wesley")));

		MvcTestResult result = mvc.get().uri("/pets/42").exchange();

		assertThat(result)
				.hasStatusOk()
				.bodyJson()
				.isLenientlyEqualTo("""
							{
								"id": 42,
								"name": "Marley",
								"owner": "Wesley"
							}
						""");

		// we also can use a resource file as expected value
		var expected = new ClassPathResource("/pets/get-by-id-response.json", Pet.class);

		assertThat(result)
				.hasStatus(HttpStatus.OK)
				.bodyJson()
				// we can use isStrictlyEqualTo if we want exact match of the JSON structure (no extra fields)
				.isLenientlyEqualTo(expected);

		// or convert to object and do assertions
		assertThat(result)
				.hasStatus(HttpStatus.OK)
				.bodyJson()
				.convertTo(Pet.class)
				.satisfies(response -> {
					assertThat(response.getId()).isEqualTo(42);
					assertThat(response.getName()).isEqualTo("Marley");
					assertThat(response.getOwner()).isEqualTo("Wesley");
				});
	}

	@Test
	public void should_return_not_found_for_non_existing_pet() throws Exception {
		given(petsRepository.findById(42))
				.willThrow(new PetNotFoundException());

		MvcTestResult result = mvc.get().uri("/pets/42").exchange();

		assertThat(result)
				.hasStatus(HttpStatus.NOT_FOUND)
				.bodyText()
				.isEmpty();

		assertThat(result)
				.hasStatus(HttpStatus.NOT_FOUND).failure()
				.isInstanceOf(PetNotFoundException.class)
				.hasMessage("Pet not found");
	}

	@Test
	public void should_create_new_pet() throws Exception {
		String requestBody = """
					{
						"name": "Marley",
						"owner": "Wesley"
					}
				""";
		var result = mvc.post()
				.uri("/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody);

		assertThat(result)
				.hasStatus(HttpStatus.CREATED)
				.bodyJson();

		ArgumentCaptor<Pet> argCaptor = ArgumentCaptor.forClass(Pet.class);
		verify(petsRepository).save(argCaptor.capture());
		Pet pet = argCaptor.getValue();

		assertThat(pet.getId()).isEqualTo(0);
		assertThat(pet.getName()).isEqualTo("Marley");
		assertThat(pet.getOwner()).isEqualTo("Wesley");
	}

	@Test
	public void should_add_api_version_header() throws Exception {
		MvcTestResult result = mvc.get().uri("/pets/42").exchange();

		assertThat(result)
				.hasStatusOk()
				.headers()
				.hasValue("X-PETS-VERSION", "v1");
	}
}