package com.github.wesleyegberto.springrestexceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "We can't handle your request =(")
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 4179924188622327712L;
}
