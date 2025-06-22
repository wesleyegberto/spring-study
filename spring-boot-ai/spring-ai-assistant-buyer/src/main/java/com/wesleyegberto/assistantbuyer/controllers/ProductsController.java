package com.wesleyegberto.assistantbuyer.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class ProductsController {
	private final ChatClient ai;

	ProductsController(ChatClient ai) {
		this.ai = ai;
	}

	@PostMapping("/users/{username}/assistant-buyer")
	String inquire(@PathVariable String username, @RequestBody Prompt prompt) {
		return ai.prompt()
				.user(prompt.question())
				.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
				.call()
				.content();
	}
}

record Prompt(String question) {}
