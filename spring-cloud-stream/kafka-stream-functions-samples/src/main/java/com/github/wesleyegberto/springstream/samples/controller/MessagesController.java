package com.github.wesleyegberto.springstream.samples.controller;

import java.util.Map;
import java.util.Objects;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MessagesController {
	private KafkaTemplate<String, Object> kafkaTemplate;

	public MessagesController(KafkaTemplate<String, Object> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	@PostMapping("messages")
	public void sendMessage(@RequestBody Map<String, String> params) {
		String key = params.getOrDefault("key", null);
		String content = params.getOrDefault("word", "WORD_NOT_PROVIDED");
		this.kafkaTemplate.send("words", key, content);
	}
	
	@PostMapping("regions")
	public void sendRegion(@RequestBody Map<String, String> params) {
		String user = params.getOrDefault("key", null);
		Objects.requireNonNull(user, "key is required");
		String region = params.getOrDefault("region", "REGION_NOT_PROVIDED");
		this.kafkaTemplate.send("regions", user, region);
	}

	@PostMapping("clicks")
	public void sendClick(@RequestBody Map<String, String> params) {
		String user = params.getOrDefault("key", null);
		Objects.requireNonNull(user, "key is required");
		String userClicks = params.getOrDefault("clicks", "1");
		this.kafkaTemplate.send("clicks", user, userClicks);
	}
}
