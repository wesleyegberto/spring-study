package com.wesleyegberto.ecommerce.orders.placer;

import com.wesleyegberto.ecommerce.events.OrderEventPublisher;
import com.wesleyegberto.ecommerce.events.OrderPlacedEvent;
import com.wesleyegberto.ecommerce.orders.management.OrderRepository;
import com.wesleyegberto.ecommerce.users.UserRepository;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class OrderPlacerService {
	private final OrderRepository orders;
	private final UserRepository users;
	private final OrderEventPublisher eventPublisher;

	public OrderPlacerService(OrderRepository orders, UserRepository users,
			OrderEventPublisher eventPublisher) {
		this.orders = orders;
		this.users = users;
		this.eventPublisher = eventPublisher;
	}

	public PlacedOrder placeOrder(@Valid OrderPlaceRequest orderRequest) {
		var client = users.findByTaxId(orderRequest.getClientTaxId());
		var order = orderRequest.toEntity(client);

		orders.save(order);
		eventPublisher.placeOrder(OrderPlacedEvent.of(order));

		return PlacedOrder.of(order);
	}
}
