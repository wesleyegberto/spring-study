package com.github.wesleyegberto.rpcserver.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.rpcserver.RpcServerApplication;
import com.github.wesleyegberto.rpcserver.service.FibonacciCalculator;

@Component
public class RequestQueueRabbitListener {
	private static final Logger LOG = LoggerFactory.getLogger(RequestQueueRabbitListener.class);
	
	private ObjectMapper mapper;
	private FibonacciCalculator calculator;

	public RequestQueueRabbitListener(ObjectMapper mapper, FibonacciCalculator calculator) {
		this.mapper = mapper;
		this.calculator = calculator;
	}

	@RabbitListener(autoStartup = "true", queues = RpcServerApplication.MQ_REQUEST_QUEUE, concurrency = "2-3", id = "rcp-server-listener")
	public String handleMessage(Message message) throws JsonProcessingException {
		LOG.info("Received message: {}", message);
		var number = -1L;
		var result = -1L;
		try {
			number = Long.parseLong(new String(mapper.readValue(message.getBody(), String.class)));
			result = calculator.calculate(number);
		} catch (Exception ex) {
			LOG.warn("Error during parsing: {}", ex.getMessage());
			// the message won't be requeued
			// throw new AmqpRejectAndDontRequeueException("Poisoned message");
			
			// the message will be requeued
			throw new RuntimeException(ex);
		}
		return mapper.writeValueAsString(result);
	}
}
