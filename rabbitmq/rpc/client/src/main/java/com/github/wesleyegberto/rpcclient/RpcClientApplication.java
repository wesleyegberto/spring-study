package com.github.wesleyegberto.rpcclient;

import java.util.UUID;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wesleyegberto.rpcclient.error.CalculationErrorHandler;
import com.github.wesleyegberto.rpcclient.error.CalculationReplyErrorHandler;

@SpringBootApplication
@EnableMongoRepositories
public class RpcClientApplication {
	private static final String EXCHANGE = "ex.request-reply";
	private static final String MQ_REQUEST_QUEUE = "mq.request.queue";
	private static final String MQ_REPLY_QUEUE = "mq.reply.queue";
	private static final String ROUTING_KEY = "test";
	
	public static void main(String[] args) {
		SpringApplication.run(RpcClientApplication.class, args);
	}

	@Bean
	public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) throws Exception {
		// remove _class
		var converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		converter.afterPropertiesSet();
		return new MongoTemplate(mongoDbFactory, converter);
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
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
	Queue requestQueue() {
		return new Queue(MQ_REQUEST_QUEUE);
	}

	@Bean
	public Queue replyQueue() {
		return new Queue(MQ_REPLY_QUEUE);
	}
	
	@Bean
	public ConnectionFactory connectionFactory() {
		return new CachingConnectionFactory();
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		template.setExchange(EXCHANGE);
		template.setRoutingKey(ROUTING_KEY);
		template.setReplyAddress(MQ_REPLY_QUEUE);
		template.setReplyTimeout(5_000);
		template.setReplyErrorHandler(new CalculationErrorHandler());
		template.setUserCorrelationId(true); // User my ID
		return template;
	}
	
	@Bean
	public SimpleMessageListenerContainer replyListenerContainer(RabbitTemplate rabbitTemplate, CalculationReplyErrorHandler errorHandler) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory());
		container.setQueues(replyQueue());
		container.setErrorHandler(errorHandler);
		container.setMessageListener(rabbitTemplate);
		container.setConsumerTagStrategy(queueName -> "rcp-client--" + UUID.randomUUID() + "--" + queueName);
		container.setConcurrency("1-20");
		container.setForceCloseChannel(true);
		container.afterPropertiesSet();
		return container;
	}
}
