package com.github.wesleyegberto.consumer.service;

import java.util.Map;

import com.github.wesleyegberto.consumer.model.SecurityEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {
	private static Logger LOG = LoggerFactory.getLogger(EventConsumer.class);

	public Map<String, String> consumeEvent(SecurityEvent securityEvent) {
		LOG.info("Security event received: {}", securityEvent);
		return Map.of(
			"id", securityEvent.getId().toString(),
			"result", "Event handled"
		);
	}
}
