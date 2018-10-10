package com.github.wesleyegberto.webasync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class WebAsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAsyncApplication.class, args);
	}
	
	@Bean
	public AsyncTaskExecutor asyncTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(100);
		executor.setQueueCapacity(1000);
		executor.setMaxPoolSize(1000);
		executor.setRejectedExecutionHandler((r, taskExecutor) -> {
			System.out.println("======================= Request reject - under load");
		});
		return executor;
	}
}
