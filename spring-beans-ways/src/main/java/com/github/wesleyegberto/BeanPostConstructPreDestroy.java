package com.github.wesleyegberto;

import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class BeanPostConstructPreDestroy {
	@PostConstruct
	public void pre() {
		System.out.println("BeanPostConstructPreDestroy - PostConstruct");
	}

	public void sayHello() {
		System.out.println("Hello World, BeanPostConstructPreDestroy!");
	}

	@PreDestroy
	public void post() {
		System.out.println("BeanPostConstructPreDestroy - PreDestroy");
	}
}