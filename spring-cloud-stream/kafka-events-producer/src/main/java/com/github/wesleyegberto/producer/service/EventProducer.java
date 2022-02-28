package com.github.wesleyegberto.producer.service;

import com.github.wesleyegberto.producer.model.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {
	private static final Logger LOG = LoggerFactory.getLogger(EventProducer.class);

	public Message<Order> createBuyOrder() {
		var amount = 100 + (int)(Math.random() * 10);
		var buyOrder = Order.ofBuy("PS4", amount);
		LOG.info("Sending order: {}", buyOrder);
		return MessageBuilder.withPayload(buyOrder)
				.setHeader(KafkaHeaders.MESSAGE_KEY, buyOrder.getId())
				.build();
	}

	public Message<Order> createSellOrder() {
		var amount = 100 + (int)(Math.random() * 10);
		var sellOrder = Order.ofSell("PS4", amount);
		LOG.info("Sending order: {}", sellOrder);
		return MessageBuilder.withPayload(sellOrder)
				.setHeader(KafkaHeaders.MESSAGE_KEY, sellOrder.getId())
				.build();
	}

}
