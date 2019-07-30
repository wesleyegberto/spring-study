package com.github.wesleyegberto.rpcclient.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

public class CalculationErrorHandler implements ErrorHandler {
	private static final Logger LOG = LoggerFactory.getLogger(CalculationErrorHandler.class);

	@Override
	public void handleError(Throwable t) {
		Throwable cause = t;
		if (t.getCause() != null) {
			cause = t.getCause();
		}
		// t.printStackTrace();
		LOG.error("Error during calculation: {}", cause.getMessage());
	}

}
