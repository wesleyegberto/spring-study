package com.github.wesleyegberto.consumer;

import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.consumer.model.SecurityEvent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.MessageBuilder;

@SpringBootTest(classes = ConsumerApplication.class)
@Import(TestChannelBinderConfiguration.class)
class ConsumerTests {
	@Autowired
	InputDestination inputDestination;

	@Autowired
	OutputDestination outputDestination;

	@Test
	public void shouldProcessMessageAndPublishResult() throws JsonMappingException, JsonProcessingException {
		String id = "c870769c-3702-4837-b482-7387a56ac277";
		var event = new SecurityEvent(UUID.fromString(id), "TEST", "Event test");
		var message = MessageBuilder.withPayload(event).build();
		inputDestination.send(message);

		var response = outputDestination.receive();
		Assertions.assertNotNull(response);
		Assertions.assertNotNull(response.getPayload());
		Assertions.assertTrue(response.getPayload().length > 0);

		var payload = new String(response.getPayload());
		var result = new ObjectMapper().readValue(payload, Map.class);
		Assertions.assertEquals(id, result.get("id"));
		Assertions.assertEquals("Event handled", result.get("result"));
	}
}
