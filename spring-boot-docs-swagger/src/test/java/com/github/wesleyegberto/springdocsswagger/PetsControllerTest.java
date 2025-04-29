package com.github.wesleyegberto.springdocsswagger;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

// @WebMvcTest(PetsController.class)
public class PetsControllerTest {
	@Autowired
	private MockMvc mvc;

	// @Test
	public void shouldCreatePet() throws Exception {
		mvc.perform(
				post("/api/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Marley\",\"breed\":\"Shitzu\"}")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("id", is(1)));
	}
}
