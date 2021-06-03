package com.github.wesleyegberto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

@SpringBootApplication
public class StartupTimeApplication {
	public static void main(String[] args) {
		var app = new SpringApplication(StartupTimeApplication.class);
		// on simple spring boot will be collected 300
		app.setApplicationStartup(new BufferingApplicationStartup(1000));
		app.run(args);
	}
}
