package com.github.wesleyegberto.springamqp.mq;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;

import com.github.wesleyegberto.springamqp.consumer.MessageReceiver;
import com.github.wesleyegberto.springamqp.consumer.NotificationReceiver;
import com.github.wesleyegberto.springamqp.entity.Message;
import com.github.wesleyegberto.springamqp.entity.Notification;

@Configuration
public class MQConfiguration {
	@Autowired
	private MqProperties mqProps;

	/**
	 * Configure the mappers.
	 * Will use the header __TypeId__ with the value to selected the class to convert. 
	 */
	@Bean
	public DefaultClassMapper classMapper() {
	    DefaultClassMapper classMapper = new DefaultClassMapper();
	    Map<String, Class<?>> idClassMapping = new HashMap<>();
	    idClassMapping.put("message", Message.class);
	    idClassMapping.put("notification", Notification.class);
	    classMapper.setIdClassMapping(idClassMapping);
	    return classMapper;
	}
	
	@Bean
	MessageConverter jsonMessageConverter() {
		Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
		converter.setClassMapper(classMapper());
	    return converter;
	}
	
	// One factory for Producers and another for Consumers (avoid blocking - https://www.rabbitmq.com/memory.html)
	@Bean @Primary
	ConnectionFactory producersConnectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory(mqProps.getMqHost(), mqProps.getMqPort());
		factory.setUsername(mqProps.getMqUser());
		factory.setPassword(mqProps.getMqPwd());
		return factory;
	}
	
	@Bean @Qualifier("consumersConnectionFactory")
	CachingConnectionFactory consumersConnectionFactory() {
		CachingConnectionFactory factory = new CachingConnectionFactory(mqProps.getMqHost(), mqProps.getMqPort());
		factory.setUsername(mqProps.getMqUser());
		factory.setPassword(mqProps.getMqPwd());
		return factory;
	}
	
	@Bean
	List<Queue> queues() {
		return Arrays.asList(new Queue(mqProps.getMqNotificationQueue()), new Queue(mqProps.getMqMessageQueue()));
	}
	
	@Bean
	List<Exchange> exchanges() {
		return Arrays.asList(new TopicExchange(mqProps.getMqNotificationExchange()), new TopicExchange(mqProps.getMqMessageExchange()));
	}

	@Bean
	List<Declarable> notificationBinding(AmqpAdmin admin) {
		return Arrays.<Declarable>asList(
			BindingBuilder.bind(new Queue(mqProps.getMqNotificationQueue()))
				.to(new TopicExchange(mqProps.getMqNotificationExchange()))
				.with(""),
			BindingBuilder.bind(new Queue(mqProps.getMqMessageQueue()))
				.to(new TopicExchange(mqProps.getMqMessageExchange()))
				.with("")
		);
	}
	
	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory producersConnectionFactory, MessageConverter jsonMessageConverter) {
		RabbitTemplate rabbitTemplate = new RabbitTemplate(producersConnectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter);
		return rabbitTemplate;
	}

	@Bean
	SimpleMessageListenerContainer buildMessageContainer(@Qualifier("consumersConnectionFactory") ConnectionFactory consumersConnectionFactory, MessageReceiver receiver) {
		MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(receiver, jsonMessageConverter());
		listenerAdapter.setDefaultListenerMethod("handleMessage");
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setAutoDeclare(true);
		container.setConnectionFactory(consumersConnectionFactory);
		container.setQueueNames(mqProps.getMqMessageQueue());
		container.setMessageConverter(jsonMessageConverter());
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	SimpleMessageListenerContainer buildNotificationContainer(@Qualifier("consumersConnectionFactory") ConnectionFactory consumersConnectionFactory, NotificationReceiver receiver) {
		MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(receiver, jsonMessageConverter());
		listenerAdapter.setDefaultListenerMethod("handleMessage");
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(consumersConnectionFactory);
		container.setQueueNames(mqProps.getMqNotificationQueue());
		container.setAutoDeclare(true);
		container.setMessageConverter(jsonMessageConverter());
		container.setMessageListener(listenerAdapter);
		// will not requeue
		container.setDefaultRequeueRejected(false);
		// scale control (see https://docs.spring.io/spring-amqp/reference/htmlsingle/#_simplemessagelistenercontainer)
		container.setConsecutiveIdleTrigger(3);
		container.setConsecutiveActiveTrigger(5);
		container.setStartConsumerMinInterval(2);
		// container.setConcurrency("2-5"); is the same as:
		container.setConcurrentConsumers(2);
		container.setMaxConcurrentConsumers(5);
		return container;
	}
	
	@Bean
   SimpleRabbitListenerContainerFactory customListenerContainerFactory(@Qualifier("consumersConnectionFactory") ConnectionFactory consumersConnectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(consumersConnectionFactory);
		factory.setConsecutiveActiveTrigger(3);
		factory.setConsecutiveIdleTrigger(2);
		factory.setDefaultRequeueRejected(false);
		factory.setStartConsumerMinInterval(1L);
		factory.setConcurrentConsumers(2);
		factory.setMaxConcurrentConsumers(5);
		return factory;
	}
	
	@Bean
	public RetryOperationsInterceptor interceptor() {
		return RetryInterceptorBuilder.stateless()
				.maxAttempts(5)
				.recoverer((message, cause) -> {
					System.out.println("Retry error recovery: " + message.getBody() + " - Exception: " + cause.getMessage());
				})
				.backOffOptions(1000, 2.0, 10000) // initialInterval, multiplier, maxInterval
				.build();
	}
}
