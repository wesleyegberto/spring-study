package com.github.wesleyegberto.producer.service;

import com.github.wesleyegberto.producer.model.SecurityEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventConsumer {
	private static Logger LOG = LoggerFactory.getLogger(EventConsumer.class);

	public void consumeEvent(SecurityEvent securityEvent) {
		LOG.info("Security event received: {}", securityEvent);
	}

	public void logEvent(SecurityEvent securityEvent) {
		LOG.info("Security event logged: {}", securityEvent);
	}

	public SecurityEvent processEvent(SecurityEvent securityEvent) {
		LOG.warn("Security event processed: {}", securityEvent);
		LOG.warn("\tBlocking account");
		return SecurityEvent.ofAction("Blocked");
	}
}
