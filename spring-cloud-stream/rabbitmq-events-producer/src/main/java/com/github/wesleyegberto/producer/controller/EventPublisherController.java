package com.github.wesleyegberto.producer.controller;

import java.util.Map;

import com.github.wesleyegberto.producer.model.SecurityEvent;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class EventPublisherController {

	private final StreamBridge streamBridge;

	public EventPublisherController(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	@PostMapping("events")
	public ResponseEntity<Boolean> sendEvent(@RequestBody Map<String, String> payload) {
		var event = SecurityEvent.ofAlert(payload.getOrDefault("message", "No payload provided"));
		var message = MessageBuilder.withPayload(event).build();
		var sent = this.streamBridge.send("securityEventSupplier-out-0", message);
		return ResponseEntity.ok(sent);
	}
}
