package com.github.wesleyegberto.rpcclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wesleyegberto.rpcclient.service.FibonacciService;

@RestController
@RequestMapping("/messages")
public class FibonacciController {
	private FibonacciService service;

	@Autowired
	public FibonacciController(FibonacciService service) {
		this.service = service;
	}

	@GetMapping("{number}")
	public String calculate(@PathVariable("number") int number) {
		return service.createMessage(number);
	}

	@GetMapping("pending/{correlationId}")
	public String getCalculationByCorrelationid(@PathVariable("correlationId") String correlationId) {
		return service.getCalculationByCorrelationid(correlationId);
	}
}
