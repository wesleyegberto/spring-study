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

import com.github.wesleyegberto.springamqp.consumer.MessageReceiver;
import com.github.wesleyegberto.springamqp.consumer.NotificationReceiver;
import com.github.wesleyegberto.springamqp.entity.Message;
import com.github.wesleyegberto.springamqp.entity.Notification;

@Configuration
public class MQConfiguration {
	@Autowired
	private MqProperties mqProps;

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
		container.setMessageConverter(jsonMessageConverter());
		container.setMessageListener(listenerAdapter);
		return container;
	}
}
