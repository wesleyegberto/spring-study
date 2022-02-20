package com.github.wesleyegberto.producer.service;

import com.github.wesleyegberto.producer.model.SecurityEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {
	private static final Logger LOG = LoggerFactory.getLogger(EventProducer.class);

	public Message<SecurityEvent> createEvent() {
		var event = SecurityEvent.ofAlert("Someone tried to access");
		return MessageBuilder.withPayload(event)
				.setHeader("to_process", true)
				.build();
	}

	public Message<SecurityEvent> createEventRegion() {
		var region = Math.random() > 0.5 ? "1" : "0";
		LOG.info("Sending message to {}", region);
		var event = SecurityEvent.ofRegion(region, "Password changed");
		return MessageBuilder.withPayload(event)
				.build();
	}

}
