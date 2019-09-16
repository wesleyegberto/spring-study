package com.github.wesleyegberto.springstudy.queueproducer.messaging.invoicing;

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
public class OrderInvoicer implements MessageListener {
	private static final Logger LOG = LoggerFactory.getLogger(OrderInvoicer.class);

	@Value("${spring.rabbitmq.custom.order-invoiced.exchange}")
	private String exchange;
	@Value("${spring.rabbitmq.custom.order-invoiced.queueRoutingKey}")
	private String routingKey;

	@Autowired
	private OrderSerializer orderSerializer;
	@Autowired
	private RabbitTemplateHandler rabbitTemplateHandler;

	@RabbitListener(containerFactory = OrderEvents.PROCESSED, queues = "${spring.rabbitmq.custom.order-processed.queue}")
	@EnableRabbitRetryAndDlq(event = OrderEvents.PROCESSED)
	public void onMessage(Message message) {
		LOG.info("Received message");

		var order = orderSerializer.deserialize(message);
		if (order == null) {
			return;
		}
		
		process(order);
		sendProcessedOrder(order);
	}

	private void process(Order order) {
		LOG.info("{} - Invoicing order", order.getOrderNumber());
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
		order.setInvoiced();
	}

	private void sendProcessedOrder(Order order) {
		var message = orderSerializer.serializeAndCreateMessage(order);
		
		LOG.info("{} - Sending order to shipping", order.getOrderNumber());
		this.rabbitTemplateHandler.getRabbitTemplate(OrderEvents.INVOICED)
			.send(exchange, routingKey, message);
	}
}
