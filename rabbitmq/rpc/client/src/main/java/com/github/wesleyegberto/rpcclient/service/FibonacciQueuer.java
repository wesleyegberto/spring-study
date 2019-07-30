package com.github.wesleyegberto.rpcclient.service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.rpcclient.entity.Calculation;

@Service
public class FibonacciQueuer {
	private static final Logger LOG = LoggerFactory.getLogger(FibonacciQueuer.class);

	private static final AtomicLong ERROR_COUNTER = new AtomicLong();

	public RabbitTemplate rabbitTemplate;
	public ObjectMapper mapper;

	public FibonacciQueuer(RabbitTemplate rabbitTemplate, ObjectMapper mapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.mapper = mapper;
	}

	public void calculateUsingMq(Calculation calculation) {
		LOG.info("Sending to queue");
		Message replyMessage = null;
		try {
			replyMessage = sendAndWaitReplay(calculation);
			LOG.info("Received from MQ: {}", replyMessage);
		} catch (JsonProcessingException ex) {
			LOG.error("Error during serialization: {}", ex.getMessage());
			throw new RuntimeException(ex);
		}
		
		handleReplyMessage(calculation, replyMessage);
	}

	public void handleReplyMessage(Calculation calculation, Message replyMessage) {
		try {
			if (replyMessage == null) {
				throw new NonResponseException("Tempo de espera da resposta esgotado.");
			}
			calculation.setProcessingMessageProperties(replyMessage.getMessageProperties());
			calculation.setResult(parseMessageResult(replyMessage));
		} catch (NonResponseException ex) {
			LOG.error("Non reponse from calculation with transaction ID {}", calculation.getTransactionId());
			ERROR_COUNTER.incrementAndGet();
			throw ex;
		} catch (Exception ex) {
			LOG.error("Error during calculation: {}", ex.getMessage());
			ERROR_COUNTER.incrementAndGet();
			throw new RuntimeException(ex);
		}
	}

	private Message sendAndWaitReplay(Calculation calculation) throws JsonProcessingException {
		var message = createRequestMessage(calculation);
		calculation.setRequestMessageProperties(message.getMessageProperties());
		return this.rabbitTemplate.sendAndReceive(message);
	}

	private Message createRequestMessage(Calculation calculation) throws JsonProcessingException {
		var properties = MessagePropertiesBuilder.newInstance()
				.setAppId("rpc-client-1")
				.setDeliveryMode(MessageDeliveryMode.PERSISTENT)
				.setCorrelationId(calculation.getTransactionId())
				.build();
		return new Message(mapper.writeValueAsBytes(calculation.getNumber()), properties);
	}

	private Long parseMessageResult(Message replyMessage) throws IOException, JsonParseException, JsonMappingException {
		var value = mapper.readValue(replyMessage.getBody(), Long.class);
		if (value == -1L) {
			throw new IllegalStateException("Number not calculated");
		}
		return value;
	}
}