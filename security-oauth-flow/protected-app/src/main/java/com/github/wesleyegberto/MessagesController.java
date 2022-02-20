package com.github.wesleyegberto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {
	@GetMapping("/messages")
	public String[] getMessages() {
		return new String[] { "Hello", "World", "Authorized" };
	}
}