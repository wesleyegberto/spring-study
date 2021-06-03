package com.github.wesleyegberto.cors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(GreetingController.class)
public class DemoApplicationTests {
	@Autowired
	private MockMvc mvc;

	@Test
	public void shouldAcceptRequestWithoutOrigin() throws Exception {
		mvc.perform(get("/greetings"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("message", is("Hello World!")));

		mvc.perform(get("/greetings/5000"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("message", is("Hello World!")));
	}

	@Test
	public void shouldAcceptRequestWithOriginLocalhost8080() throws Exception {
		mvc.perform(get("/greetings").header("Origin", "http://localhost:8080"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("message", is("Hello World!")));

		mvc.perform(get("/greetings/5000").header("Origin", "http://localhost:8080"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("message", is("Hello World!")));
	}

	@Test
	public void shouldAcceptRequestWithOriginLocalhost5000OnCustomCors() throws Exception {
		mvc.perform(get("/greetings/5000").header("Origin", "http://localhost:5000"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("message", is("Hello World!")));
	}

	@Test
	public void shouldRejectRequestWithOriginLocalhost5000OnDefaultCors() throws Exception {
		mvc.perform(get("/greetings").header("Origin", "http://localhost:5000"))
				.andExpect(status().isForbidden());
	}

	@Test
	public void shouldRejectRequestWithInvalidOrigin() throws Exception {
		mvc.perform(get("/greetings").header("Origin", "http://localhost:8090"))
				.andExpect(status().isForbidden());

		mvc.perform(get("/greetings/5000").header("Origin", "http://localhost:9090"))
				.andExpect(status().isForbidden());
	}
}
