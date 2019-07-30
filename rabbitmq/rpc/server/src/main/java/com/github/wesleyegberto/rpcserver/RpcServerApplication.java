package com.github.wesleyegberto.rpcserver;

import java.util.UUID;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.rpcserver.consumer.RequestQueueMessageHandler;
import com.github.wesleyegberto.rpcserver.error.CalculationErrorHandler;

@SpringBootApplication
public class RpcServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(RpcServerApplication.class, args);
	}
	
	public static final String EXCHANGE = "ex.request-reply";
	public static final String MQ_REQUEST_QUEUE = "mq.request.queue";
	public static final String MQ_REPLY_QUEUE = "mq.reply.queue";
	public static final String ROUTING_KEY = "test";
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(EXCHANGE);
	}

	@Bean
	Binding binding() {
		return BindingBuilder.bind(requestQueue()).to(exchange()).with(ROUTING_KEY);
	}
	
	@Bean
	public Queue requestQueue() {
		return new Queue(MQ_REQUEST_QUEUE);
	}

	@Bean
	public Queue replyQueue() {
		return new Queue(MQ_REPLY_QUEUE);
	}

	@Bean
	public SimpleMessageListenerContainer serviceListenerContainer(ConnectionFactory connectionFactory, RequestQueueMessageHandler queueConsumer) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(requestQueue());
		container.setMessageListener(new MessageListenerAdapter(queueConsumer));
		container.setErrorHandler(new CalculationErrorHandler());
		container.setConsumerTagStrategy(queue -> "rcp-client--" + UUID.randomUUID() + "--" + queue);
		container.afterPropertiesSet();
		return container;
	}
}
