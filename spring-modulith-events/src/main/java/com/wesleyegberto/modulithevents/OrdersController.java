package com.wesleyegberto.modulithevents;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
	private final OrdersRepository ordersRepository;
	private final ApplicationEventPublisher eventPublisher;

	public OrdersController(OrdersRepository ordersRepository, ApplicationEventPublisher eventPublisher) {
		this.ordersRepository = ordersRepository;
		this.eventPublisher = eventPublisher;
	}

	@PostMapping
	public ResponseEntity<Order> create(@RequestBody @Validated Order order) {
		ordersRepository.save(order);
		Logger.log("Order saved with ID " + order.getId());
		eventPublisher.publishEvent(OrderEvent.created(order));
		return ResponseEntity.ok(order);
	}
}
