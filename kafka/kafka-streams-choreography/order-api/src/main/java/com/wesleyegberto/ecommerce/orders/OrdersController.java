package com.wesleyegberto.ecommerce.orders;

import com.wesleyegberto.ecommerce.orders.placer.OrderPlaceRequest;
import com.wesleyegberto.ecommerce.orders.placer.OrderPlacerService;
import com.wesleyegberto.ecommerce.users.ClientNotFoundException;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("orders")
public class OrdersController {
	private final OrderPlacerService processingService;

	public OrdersController(OrderPlacerService processingService) {
		this.processingService = processingService;
	}

	@PostMapping
	public ResponseEntity<?> placeOrder(@RequestBody @Valid OrderPlaceRequest order) {
		try {
			var placedOrder = processingService.placeOrder(order);
			return ResponseEntity.ok(placedOrder);
		} catch (ClientNotFoundException ex) {
			return ResponseEntity.badRequest().body(
				Map.of("error", ex.getMessage())
			);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return ResponseEntity.internalServerError().body(
				Map.of("error", ex.getMessage())
			);
		}
	}
}
