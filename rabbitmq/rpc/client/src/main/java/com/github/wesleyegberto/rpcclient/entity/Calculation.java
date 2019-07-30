package com.github.wesleyegberto.rpcclient.entity;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Calculations")
public class Calculation {
	@Id
	private UUID id;

	@Indexed(unique = true)
	private String transactionId;

	private LocalDateTime createDate;
	private LocalDateTime updateDate;

	private Status status;
	private Long number;
	private Long result;

	private boolean errorOccurred;
	private String errorMessage;

	private boolean delayedReply;
	private LocalDateTime replayDate;

	private Map<String, String> requestMessageProperties;
	private Map<String, String> processingMessageProperties;

	public Calculation() {
	}

	public Calculation(long number) {
		this.id = UUID.randomUUID();
		this.transactionId = UUID.randomUUID().toString();
		this.createDate = LocalDateTime.now();
		this.status = Status.PENDING;
		this.number = number;
	}

	public static Calculation of(long number) {
		return new Calculation(number);
	}

	public UUID getId() {
		return id;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getUpdateDate() {
		return updateDate;
	}

	public Status getStatus() {
		return status;
	}

	public Long getNumber() {
		return number;
	}

	public Long getResult() {
		return result;
	}

	public boolean isDelayedReply() {
		return delayedReply;
	}

	public LocalDateTime getReplayDate() {
		return replayDate;
	}

	public boolean isErrorOccurred() {
		return errorOccurred;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Map<String, String> getRequestMessageProperties() {
		return requestMessageProperties;
	}

	public void setRequestMessageProperties(MessageProperties properties) {
		this.requestMessageProperties = copyProperties(properties);
	}

	public Map<String, String> getProcessingMessageProperties() {
		return processingMessageProperties;
	}

	public void setProcessingMessageProperties(MessageProperties properties) {
		this.processingMessageProperties = copyProperties(properties);
	}

	private Map<String, String> copyProperties(MessageProperties properties) {
		var copy = new LinkedHashMap<String, String>();
		copy.put("appId", properties.getAppId());
		copy.put("correlationId", properties.getCorrelationId());
		copy.put("replyTo", properties.getReplyTo());
		copy.put("contentType", properties.getContentType());
		copy.put("contentEncoding", properties.getContentEncoding());
		copy.put("deliveryMode", String.valueOf(properties.getDeliveryMode()));
		copy.put("receivedRoutingKey", properties.getReceivedRoutingKey());
		copy.put("receivedExchange", properties.getReceivedExchange());
		copy.put("receivedDeliveryMode", String.valueOf(properties.getReceivedDeliveryMode()));
		copy.put("deliveryTag", String.valueOf(properties.getDeliveryTag()));
		copy.put("consumerTag", properties.getConsumerTag());
		copy.put("consumerQueue", properties.getConsumerQueue());
		copy.put("publishSequenceNumber", String.valueOf(properties.getPublishSequenceNumber()));
		return copy;
	}

	public void setResult(Long result) {
		this.result = result;
		this.replayDate = LocalDateTime.now();
		this.status = Status.CALCULATED;
	}

	public void setError(String errorMessage) {
		setStatusWithError(Status.ERROR, errorMessage);
	}

	public void setTimedOut(String errorMessage) {
		setStatusWithError(Status.REPLY_TIMEOUT, errorMessage);
	}

	private void setStatusWithError(Status status, String errorMessage) {
		this.errorOccurred = true;
		this.errorMessage = errorMessage;
		this.replayDate = LocalDateTime.now();
		this.status = status;
	}

	public void setDelayedReply() {
		this.delayedReply = true;
	}
}
