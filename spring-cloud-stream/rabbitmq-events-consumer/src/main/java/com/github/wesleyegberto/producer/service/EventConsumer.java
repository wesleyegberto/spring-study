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
}
