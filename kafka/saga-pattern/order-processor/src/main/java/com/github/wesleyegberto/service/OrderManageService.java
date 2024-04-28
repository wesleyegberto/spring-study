package com.github.wesleyegberto.service;

import com.github.wesleyegberto.model.Order;

import org.springframework.stereotype.Service;

@Service
public class OrderManageService {

	public Order confirm(Order orderPayment, Order orderStock) {
		var order = new Order(
			orderPayment.getId(),
			orderPayment.getCustomerId(),
			orderPayment.getProductId(),
			orderPayment.getProductCount(),
			orderPayment.getPrice()
		);

		if (isAccepted(orderPayment) && isAccepted(orderStock)) {
			order.setStatus("CONFIRMED");

		} else if (isRejected(orderPayment) && isRejected(orderStock)) {
			order.setStatus("REJECTED");

		} else if (isRejected(orderPayment) || isRejected(orderStock)) {
			String source = isRejected(orderPayment) ? "PAYMENT" : "STOCK";
			order.setStatus("ROLLBACK");
			order.setSource(source);
		}
		return order;
	}

	public boolean isAccepted(Order order) {
		return order.getStatus().equals("ACCEPTED");
	}

	public boolean isRejected(Order order) {
		return order.getStatus().equals("REJECTED");
	}
}
