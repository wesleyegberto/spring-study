package com.github.wesleyegberto.springstudy.queueproducer.messaging.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.wesleyegberto.springstudy.queueproducer.model.Order;
import com.github.wesleyegberto.springstudy.queueproducer.model.OrderEvents;
import com.github.wesleyegberto.springstudy.queueproducer.model.OrderSerializer;
import com.tradeshift.amqp.annotation.EnableRabbitRetryAndDlq;
import com.tradeshift.amqp.rabbit.handlers.RabbitTemplateHandler;

@Component
public class OrderProcessor implements MessageListener {
	private static final Logger LOG = LoggerFactory.getLogger(OrderProcessor.class);

	@Value("${spring.rabbitmq.custom.order-processed.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.custom.order-processed.queueRoutingKey}")
	private String routingKey;
	
	@Autowired
	private OrderSerializer orderSerializer;
	@Autowired
	private RabbitTemplateHandler rabbitTemplateHandler;

	/**
	 * ACK: {"orderNumber":11223344,"orderDate":"2019-09-15T13:18:30.044454","sku":"XPTO-1","total":1999}
	 * Retry: {"orderNumber":33445566,"orderDate":"2019-09-15T13:18:30.044454","sku":"XPTO-2","total":1999}
	 * DLQ: {"orderNumber":44332211,"orderDate":"2019-09-15T13:18:30.044454","sku":"XPTO-0","total":1999}
	 */
	@RabbitListener(containerFactory = OrderEvents.RECEIVED, queues = "${spring.rabbitmq.custom.order-received.queue}")
	@EnableRabbitRetryAndDlq(event = OrderEvents.RECEIVED,
		directToDlqWhen = { SkuOutOfStockException.class }
	)
	public void onMessage(Message message) {
		LOG.info("Received message");
		
		var order = orderSerializer.deserialize(message);
		if (order == null) {
			LOG.info("Order is null");
			return;
		}
		
		process(order);
		sendProcessedOrder(order);
	}

	private void process(Order order) {
		LOG.info("{} - SKU {} - Processing order", order.getOrderNumber(), order.getSku());
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
		}
		
		if (order.getSku().equals("XPTO-0")) {
			LOG.warn("{} - SKU out of stock", order.getOrderNumber());
			throw new SkuOutOfStockException("SKU " + order.getSku() + " out of stock");
		}
		if (order.getSku().equals("XPTO-2")) {
			LOG.warn("{} - Error during stock verification", order.getOrderNumber());
			throw new RuntimeException("Error during stock verification for SKU " + order.getSku());
		}
		
		order.setProcessed();
	}

	private void sendProcessedOrder(Order order) {
		var message = orderSerializer.serializeAndCreateMessage(order);
		
		LOG.info("{} - Sending order to invoicing", order.getOrderNumber());
		this.rabbitTemplateHandler.getRabbitTemplate(OrderEvents.PROCESSED)
			.send(exchange, routingKey, message);
	}
}
