package com.github.wesleyegberto.springamqp.mq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqProperties {
	@Value("${mq.host}")
	private String mqHost;
	@Value("${mq.port}")
	private int mqPort;
	
	@Value("${mq.user}")
	private String mqUser;
	@Value("${mq.pwd}")
	private String mqPwd;
	
	@Value("${mq.message.exchange}")
	private String mqMessageExchange;
	@Value("${mq.message.queue}")
	private String mqMessageQueue;
	@Value("${mq.message.dlx}")
	private String mqMessageDlx;

	@Value("${mq.notification.exchange}")
	private String mqNotificationExchange;
	@Value("${mq.notification.queue}")
	private String mqNotificationQueue;
	@Value("${mq.notification.dlx}")
	private String mqNotificationDlx;
	
	public String getMqHost() {
		return mqHost;
	}

	public int getMqPort() {
		return mqPort;
	}

	public String getMqUser() {
		return mqUser;
	}

	public String getMqPwd() {
		return mqPwd;
	}

	public String getMqMessageExchange() {
		return mqMessageExchange;
	}

	public String getMqMessageQueue() {
		return mqMessageQueue;
	}

	public String getMqMessageDlx() {
		return mqMessageDlx;
	}
	
	public String getMqNotificationExchange() {
		return mqNotificationExchange;
	}
	
	public String getMqNotificationQueue() {
		return mqNotificationQueue;
	}
	
	public String getMqNotificationDlx() {
		return mqNotificationDlx;
	}
}
