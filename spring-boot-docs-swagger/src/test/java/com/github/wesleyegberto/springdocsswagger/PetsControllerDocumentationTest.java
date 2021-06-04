package com.github.wesleyegberto.springdocsswagger;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(PetsController.class)
@ExtendWith(RestDocumentationExtension.class)
public class PetsControllerDocumentationTest {
	private MockMvc mvc;

	// will mount the requets and prepare the docs
	private RestDocumentationResultHandler resultHandler;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
		//@formatter:off
		this.resultHandler = MockMvcRestDocumentation.document("{method-name}",
			Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
			Preprocessors.preprocessResponse(
				Preprocessors.prettyPrint(),
				Preprocessors.removeMatchingHeaders("X.*", "Pragma", "Expires")
			)
		);
		this.mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
				.alwaysDo(this.resultHandler)
				.build();
		//@formatter:on
	}

	@Test
	public void shouldCreatePet() throws Exception {
		mvc.perform(
				RestDocumentationRequestBuilders.post("/api/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Marley\",\"breed\":\"Shitzu\"}")
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("id", is(1)))
			.andDo(resultHandler.document(
				requestFields(
					fieldWithPath("name").description("The name of the pet."),
					fieldWithPath("breed").description("The breed of the pet."),
					fieldWithPath("birthDate").optional().type(JsonFieldType.STRING).description("The birth date of the pet, formatted as yyyy-MM-dd.")
				),
				responseFields(
					fieldWithPath("id").description("The ID of the pet."),
					fieldWithPath("createDate").optional().type(JsonFieldType.STRING).description("The date the pet was registered, formatted as yyyy-MM-ddTHH-mm-ss.ssssss."),
					fieldWithPath("name").description("The name of the pet."),
					fieldWithPath("breed").description("The breed of the pet."),
					fieldWithPath("birthDate").optional().type(JsonFieldType.STRING).description("The birth date of the pet, formatted as yyyy-MM-dd."),
					fieldWithPath("owner").optional().type(JsonFieldType.OBJECT).description("The owner of the pet")
				)
			))
			;
	}
}
