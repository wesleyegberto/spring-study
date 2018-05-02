package com.github.wesleyegberto;

import org.springframework.stereotype.Component;

@Component
public class BeanDeclaredByAnnotation {
	public void sayHello() {
		System.out.println("Hello, World from BeanDeclaredByAnnotation!");
	}
}