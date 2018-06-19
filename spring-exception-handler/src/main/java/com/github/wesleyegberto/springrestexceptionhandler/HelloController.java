package com.github.wesleyegberto.springrestexceptionhandler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
	@GetMapping("successful")
	public String successfulCall() {
		return "Hello!";
	}

	@GetMapping("failure")
	public String failureCall() {
		throw new RuntimeException("An error that can't be handled");
	}

	@GetMapping("graceful")
	public String gracefulFailure() {
		throw new IllegalStateException("An error that can be handled");
	}

	@GetMapping("custom")
	public String customResponseFailure() {
		throw new CustomException();
	}
}