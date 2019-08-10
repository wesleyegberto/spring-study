package com.github.wesleyegberto.cors;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/greetings")
public class GreetingController {

	@GetMapping
	public Greeting greetingDefaultCors(@RequestParam(name = "name", defaultValue = "World") String name) {
		return new Greeting(String.format("Hello %s!", name));
	}

	@GetMapping("5000")
	@CrossOrigin(origins = "http://localhost:5000") // Append origin to existing rule (8080)
	public Greeting greetingCustomCors(@RequestParam(name = "name", defaultValue = "World") String name) {
		return new Greeting(String.format("Hello %s!", name));
	}
}

class Greeting {
	private String message;

	public Greeting(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}