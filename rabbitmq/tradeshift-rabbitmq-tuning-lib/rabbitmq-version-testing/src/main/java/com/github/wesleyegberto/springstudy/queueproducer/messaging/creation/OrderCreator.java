package com.github.wesleyegberto.springstudy.queueproducer.messaging.creation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.wesleyegberto.springstudy.queueproducer.model.Order;
import com.github.wesleyegberto.springstudy.queueproducer.model.OrderEvents;
import com.github.wesleyegberto.springstudy.queueproducer.model.OrderSerializer;
import com.tradeshift.amqp.rabbit.handlers.RabbitTemplateHandler;

@Component
class OrderCreator {
	private static final Logger LOG = LoggerFactory.getLogger(OrderCreator.class);

	@Value("${spring.rabbitmq.custom.order-received.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.custom.order-received.queueRoutingKey}")
	private String routingKey;
	
	@Autowired
	private OrderNumberGenerator numberGenerator;
	
	@Autowired
	private RabbitTemplateHandler rabbitTemplateHandler;
	@Autowired
	private OrderSerializer orderSerializer;

	@Scheduled(cron = "*/1 * * * * *")
	public void createOrder() {
		LOG.info("Generating new order");
		var order = new Order(numberGenerator.generateOrderNumber(), 1999);
		
		sendReceivedOrder(order);
	}

	private void sendReceivedOrder(Order order) {
		var message = orderSerializer.serializeAndCreateMessage(order);
		
		LOG.info("{} - Sending order to processing", order.getOrderNumber());
		this.rabbitTemplateHandler.getRabbitTemplate(OrderEvents.RECEIVED)
			.send(exchange, routingKey, message);
	}
}