package com.github.wesleyegberto.producer.service;

import com.github.wesleyegberto.producer.model.Order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class EventProducer {
	private static final Logger LOG = LoggerFactory.getLogger(EventProducer.class);

	public Message<Order> createBuyOrder() {
		var buyOrder = Order.ofBuy(100);
		LOG.info("Sending order: {}", buyOrder);
		return MessageBuilder.withPayload(buyOrder)
				// .setHeader(KafkaHeaders.MESSAGE_KEY, event.getId().toString())
				.build();
	}

	public Message<Order> createSellOrder() {
		var sellOrder = Order.ofSell(105);
		LOG.info("Sending order: {}", sellOrder);
		return MessageBuilder.withPayload(sellOrder)
				// .setHeader(KafkaHeaders.MESSAGE_KEY, event.getId().toString())
				.build();
	}

}
