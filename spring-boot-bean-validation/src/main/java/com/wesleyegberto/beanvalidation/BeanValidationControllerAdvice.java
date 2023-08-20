package com.wesleyegberto.beanvalidation;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BeanValidationControllerAdvice {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
		var errors = new LinkedHashMap<String, String>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			errors.put(
				((FieldError) error).getField(),
				error.getDefaultMessage()
			);
		});
		return ResponseEntity.badRequest()
				.body(Map.of("errors", errors));
	}
}
