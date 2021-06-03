package com.github.wesleyegberto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanPropertyInjection {
	private BeanToBeInjected dependency;

	@Autowired
	public void setBeanToBeInjected(BeanToBeInjected beanToBeInjected) {
		this.dependency = beanToBeInjected;
	}

	public void sayHello() {
		System.out.print("Hello, World from BeanPropertyInjection with dependency: ");
		dependency.sayHello();
	}
}