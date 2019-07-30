package com.github.wesleyegberto.rpcserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.wesleyegberto.rpcserver.service.FibonacciCalculator;

@RestController
@RequestMapping("/fibonacci")
public class FibonacciController {
	private FibonacciCalculator fibonacciCalculator;

	@Autowired
	public FibonacciController(FibonacciCalculator fibonacciCalculator) {
		this.fibonacciCalculator = fibonacciCalculator;
	}

	@GetMapping("{number}")
	public long calculate(@PathVariable long number) {
		return fibonacciCalculator.calculate(number);
	}
}
