package com.github.wesleyegberto.controller;

import com.github.wesleyegberto.entity.PetNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PetExceptionHandler {

	@ExceptionHandler(PetNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleInvalidPetException() {
	}
}
