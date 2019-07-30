package com.github.wesleyegberto.rpcclient.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import com.github.wesleyegberto.rpcclient.entity.Calculation;
import com.github.wesleyegberto.rpcclient.repository.CalculationRepository;
import com.github.wesleyegberto.rpcclient.service.FibonacciQueuer;

@Component
public class CalculationReplyErrorHandler implements ErrorHandler {
	private static final Logger LOG = LoggerFactory.getLogger(CalculationErrorHandler.class);
	
	private CalculationRepository calculationRepository;
	private FibonacciQueuer queuer;
	
	public CalculationReplyErrorHandler(CalculationRepository calculationRepository, FibonacciQueuer queuer) {
		this.calculationRepository = calculationRepository;
		this.queuer = queuer;
	}

	@Override
	public void handleError(Throwable throwable) {
		Throwable cause = throwable;
		if (throwable.getCause() != null) {
			cause = throwable.getCause();
		}
		// t.printStackTrace();
		LOG.error("Reply delivery failed: {}", cause.getLocalizedMessage());
		
		if (throwable instanceof ListenerExecutionFailedException) {
			var delayedMessage = ((ListenerExecutionFailedException) throwable).getFailedMessage();
			LOG.error("Delayed message - AppId: {}, CorrelationId: {}, ConsumerTag: {}",
					delayedMessage.getMessageProperties().getAppId(),
					delayedMessage.getMessageProperties().getCorrelationId(),
					delayedMessage.getMessageProperties().getConsumerTag());
			
			if (delayedMessage.getMessageProperties().getCorrelationId() != null) {
				LOG.info("Received delayed message from MQ: {}", delayedMessage);
				handleDelayedMessage(delayedMessage);
			}
		}
	}

	private void handleDelayedMessage(Message delayedMessage) {
		Calculation calculation = null;
		var correlationId = delayedMessage.getMessageProperties().getCorrelationId();
		try {
			calculation = findCalculationUsingCorrelationid(correlationId);
			queuer.handleReplyMessage(calculation, delayedMessage);
			calculation.setDelayedReply();
			this.calculationRepository.updateResult(calculation);
		} catch (IllegalStateException ex) {
			LOG.error("Transaction ID invalid: {}", correlationId);
		} catch (Exception ex) {
			LOG.error("Erro during handling of delayed message {}: {}", correlationId, ex.getMessage());
			if (calculation != null) {
				calculation.setError(ex.getMessage());
				calculation.setDelayedReply();
				calculationRepository.updateError(calculation);
			}
		}
	}

	private Calculation findCalculationUsingCorrelationid(String correlationid) {
		var calculation = this.calculationRepository.getByTransactionid(correlationid);
		if (calculation == null)
			throw new IllegalArgumentException("Calculation not found by transaction ID " + correlationid);
		return calculation;
	}

}
