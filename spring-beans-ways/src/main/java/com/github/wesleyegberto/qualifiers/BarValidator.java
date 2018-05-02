package com.github.wesleyegberto.qualifiers;

import org.springframework.stereotype.Component;

@Component("bar")
public class BarValidator implements Validator {
	public void something() {
		System.out.println("Bar validator!");
	}
}