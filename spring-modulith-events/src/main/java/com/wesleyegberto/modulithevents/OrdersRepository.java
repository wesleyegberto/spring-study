package com.wesleyegberto.modulithevents;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class OrdersRepository {
	private static final Map<Long, Order> DATABASE = new HashMap<>();
	private static final AtomicLong ID_GENERATOR = new AtomicLong();

	public OrdersRepository() {
	}

	public void save(Order order) {
		order.setCreated(ID_GENERATOR.incrementAndGet());
		DATABASE.put(order.getId(), order);
	}

	public Optional<Order> getById(Long id) {
		if (DATABASE.containsKey(id)) {
			return Optional.of(DATABASE.get(id));
		}
		return Optional.empty();
	}
}
