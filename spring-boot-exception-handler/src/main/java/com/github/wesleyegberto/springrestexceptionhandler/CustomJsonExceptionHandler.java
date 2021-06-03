package com.github.wesleyegberto.springrestexceptionhandler;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomJsonExceptionHandler {
	// we can also use @ResponseStatus here
	@ResponseStatus(code =  HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalStateException.class)
	public String handleInvalidStateException(IllegalStateException rex, HttpServletResponse response) {
		System.err.println("Hey dev! We got an illegal state: " + rex.getMessage());
		response.addHeader("Reason", rex.getMessage());
		return "Sorry, we got some problem =(";
	}
}