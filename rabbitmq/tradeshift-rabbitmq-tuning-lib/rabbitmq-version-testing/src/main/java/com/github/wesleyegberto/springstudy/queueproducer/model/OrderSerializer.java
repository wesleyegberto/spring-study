package com.github.wesleyegberto.springstudy.queueproducer.model;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderSerializer {
	private static final Logger LOG = LoggerFactory.getLogger(OrderSerializer.class);

	@Autowired
	private ObjectMapper objectMapper;
	
	public String serialize(Order order) {
		LOG.info("{} - Serializing");

		try {
			return objectMapper.writeValueAsString(order);
		} catch (JsonProcessingException ex) {
			LOG.error("Error during serialization", ex);
			throw new RuntimeException(ex);
		}
	}
	
	public Message serializeAndCreateMessage(Order order) {
		var content = serialize(order);
		LOG.info("{} - Creating message");
		return new Message(content.getBytes(), MessagePropertiesBuilder.newInstance().build());
	}

	public Order deserialize(Message message) {
		try {
			return objectMapper.readValue(new String(message.getBody()), Order.class);
		} catch (IOException ex) {
			LOG.error("Error during deserialization", ex);
			throw new RuntimeException(ex);
		}
	}
}
