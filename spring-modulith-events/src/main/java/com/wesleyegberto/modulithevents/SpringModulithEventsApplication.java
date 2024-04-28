package com.wesleyegberto.modulithevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Event listener are processed synchronously in the same thread and transaction (if available).
 * To process asynchronously we need to use @Async.
 */
@SpringBootApplication
@EnableAsync
public class SpringModulithEventsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringModulithEventsApplication.class, args);
	}

	@EventListener
	void ready(ApplicationReadyEvent event) {
		Logger.log("Application read event after (ms): " + (event.getTimeTaken().getNano() / 1000000L));
	}

	@EventListener
	void webServerInitialized(WebServerInitializedEvent event) throws InterruptedException {
		Logger.log("Web server started on port " + event.getWebServer().getPort());
		// will hold the application startup
		Thread.sleep(1_000);
	}
}
