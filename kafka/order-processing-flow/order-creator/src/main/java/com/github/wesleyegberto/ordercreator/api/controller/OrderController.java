package com.github.wesleyegberto.ordercreator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wesleyegberto.ordercreator.api.model.ApiError;
import com.github.wesleyegberto.ordercreator.business.order.creation.entity.CreatedOrder;
import com.github.wesleyegberto.ordercreator.business.order.creation.entity.Order;
import com.github.wesleyegberto.ordercreator.business.order.creation.service.OrderCreationException;
import com.github.wesleyegberto.ordercreator.business.order.creation.service.OrderCreator;

@RestController
@RequestMapping("/orders")
public class OrderController {
	private OrderCreator orderCreator;

	@Autowired
	public OrderController(OrderCreator orderCreator) {
		this.orderCreator = orderCreator;
	}
	
	@PostMapping
	public CreatedOrder createOrder(@RequestBody Order order) {
		return orderCreator.create(order);
	}
	
	@ExceptionHandler
	public ResponseEntity<ApiError> handleException(Exception ex) {
		var status = HttpStatus.BAD_REQUEST;
		
		if (ex instanceof OrderCreationException)
			status = HttpStatus.PRECONDITION_FAILED;
		
		return new ResponseEntity<ApiError>(ApiError.fromException(ex), status);
	}
}
