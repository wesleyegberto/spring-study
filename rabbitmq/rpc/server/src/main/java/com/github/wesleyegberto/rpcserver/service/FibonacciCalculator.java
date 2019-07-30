package com.github.wesleyegberto.rpcserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FibonacciCalculator {
	private static final Logger LOG = LoggerFactory.getLogger(FibonacciCalculator.class);

	public long calculate(long number) {
		LOG.info("Calculating fibonacci of {}", number);
		if (number > 5) {
			throw new NumberTooBigException("Number is too big to calculate: " + number);
		}
		return fib(number);
	}

	private long fib(long number) {
		if (number == 0)
			return 0;
		else if (number == 1)
			return 1;
		return fib(number - 1) + fib(number - 2);
	}
}
