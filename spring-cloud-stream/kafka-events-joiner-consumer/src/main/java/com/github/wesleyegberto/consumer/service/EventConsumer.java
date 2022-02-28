package com.github.wesleyegberto.consumer.service;

import com.github.wesleyegberto.consumer.model.Order;
import com.github.wesleyegberto.consumer.model.Transaction;

import org.springframework.stereotype.Component;

@Component
public class EventConsumer {
	public Transaction tryMakeTransaction(Order buyOrder, Order sellOrder) {
		if (buyOrder.getAmount() < sellOrder.getAmount())
			return null;
		return Transaction.ofOrders(buyOrder, sellOrder);
	}
}
