package com.github.wesleyegberto.webasync.controller;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class BlockingController {
	private static final Logger logger = LoggerFactory.getLogger(BlockingController.class);
	
	@Autowired
	private AsyncTaskExecutor executor;

	@GetMapping("sync")
	public String syncBlockingHandler() {
		logger.info("== Request handled in " + Thread.currentThread().getName());

		logger.info("== Process executed in " + Thread.currentThread().getName());
		return "sync blocking =(";
	}
	
	@GetMapping("async")
	public CompletableFuture<String> asyncBlockingHandler() {
		logger.info("== Request handled in " + Thread.currentThread().getName());
		
		return CompletableFuture.supplyAsync(() -> {
			logger.info("== Process executed in " + Thread.currentThread().getName());
			// if API used here is blocking it will block, otherwise will be non-blocking =)
			
			// Servlet 3.0 - outputStream.write always block =s
			return "async blocking =/";
		}, executor);
	}
}
