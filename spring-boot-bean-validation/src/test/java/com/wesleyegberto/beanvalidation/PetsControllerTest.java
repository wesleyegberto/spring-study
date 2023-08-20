package com.wesleyegberto.beanvalidation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureJsonTesters
@WebMvcTest(PetsController.class)
public class PetsControllerTest {
	@Autowired
	private MockMvc mvc;

	@MockBean
	private PetsRepository petsRepositoy;

	@Autowired
	private JacksonTester<PetCreationDto> json;

	@Test
	public void should_save_valid_pet() throws Exception {
		mvc.perform(
				post("/pets")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								json.write(new PetCreationDto("Marley", "Wesley", "Pit Bull")).getJson())
						)
				.andExpect(MockMvcResultMatchers.status().isOk());

		ArgumentCaptor<Pet> argCaptor = ArgumentCaptor.forClass(Pet.class);
		verify(petsRepositoy).save(argCaptor.capture());
		Pet pet = argCaptor.getValue();

		assertThat(pet.getId(), is(0));
		assertThat(pet.getName(), is("Marley"));
		assertThat(pet.getOwner(), is("Wesley"));
		assertThat(pet.getBreed(), is("Pit Bull"));
	}

	@Test
	public void should_reject_pet_without_name() throws Exception {
		mvc.perform(
				post("/pets")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								json.write(new PetCreationDto("", "Wesley", "Pit Bull")).getJson())
						)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors.name", is("Name is required")));
	}

	@Test
	public void should_reject_pet_without_owner() throws Exception {
		mvc.perform(
				post("/pets")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								json.write(new PetCreationDto("Bob", "", "Pit Bull")).getJson())
						)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors.owner", is("Owner is required")));
	}

	@Test
	public void should_reject_pet_without_breed() throws Exception {
		mvc.perform(
				post("/pets")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(
								json.write(new PetCreationDto("Bob", "Wesley", "")).getJson())
						)
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors.breed", is("Breed is required")));
	}
}
