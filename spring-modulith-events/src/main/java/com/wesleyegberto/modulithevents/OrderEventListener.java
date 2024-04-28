package com.wesleyegberto.modulithevents;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.scheduling.annotation.Async;

@Configuration
public class OrderEventListener {

	/**
	 * The events are processed synchronously in the same thread and transaction.
	 * Using @Async we will process it in another thread.
	 */
	@Async
	@EventListener
	void processOrderEventInMemory(OrderEvent orderEvent) {
		Logger.log("Order event received: " + orderEvent);

		switch (orderEvent.eventType()) {
			case CREATED -> onOrderCreated(orderEvent);
		}
	}

	/**
	 * The modulith events are processed asynchrnously in another thread and transactoin.
	 * The events published are stored in DB and processed in another transaction.
	 */
	@ApplicationModuleListener
	void processOrderEventStoringDb(OrderEvent orderEvent) {
		Logger.log("Order event received from DB: " + orderEvent);
	}

	private void onOrderCreated(OrderEvent orderEvent) {
		Logger.log("Notifying customer about received order ID " + orderEvent.orderId());
		try {
			Thread.sleep(30_000);
		} catch (InterruptedException e) {
		}
		Logger.log("Notification sent for order ID " + orderEvent.orderId());
	}

}
