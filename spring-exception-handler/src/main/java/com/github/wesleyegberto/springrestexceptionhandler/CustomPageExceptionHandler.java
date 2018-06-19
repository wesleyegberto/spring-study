package com.github.wesleyegberto.springrestexceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomPageExceptionHandler {
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleNumberFormatException(NumberFormatException nfex) {
		System.err.println("Hey dev! We got an invalid format: " + nfex.getMessage());
		ModelAndView model = new ModelAndView("custom_error");
		model.addObject("errorMessage", nfex.getMessage());
		return model;
	}
}