package com.github.wesleyegberto.rpcserver.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.rpcserver.service.FibonacciCalculator;

@Component
public class RequestQueueMessageHandler {
	private static final Logger LOG = LoggerFactory.getLogger(RequestQueueMessageHandler.class);
	
	private ObjectMapper mapper;
	private FibonacciCalculator calculator;

	public RequestQueueMessageHandler(ObjectMapper mapper, FibonacciCalculator calculator) {
		this.mapper = mapper;
		this.calculator = calculator;
	}

	public String handleMessage(byte[] message) throws JsonProcessingException {
		LOG.info("Received message: {}", message);
		long number = -1;
		var result = -1L;
		try {
			number = Long.parseLong(mapper.readValue(message, String.class));
			result = calculator.calculate(number);
		} catch (Exception ex) {
			LOG.warn("Error during parsing: {}", ex.getMessage());
			// throw exception to error handler
			// throw new RuntimeException(ex);
			
			// if not handled, the message won't be requeued
			throw new AmqpRejectAndDontRequeueException("Poisoned message");
		}
		return mapper.writeValueAsString(result);
	}
}
