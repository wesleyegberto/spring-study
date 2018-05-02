package com.github.wesleyegberto;

import com.github.wesleyegberto.qualifiers.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BeanInjectingWithQualifiers {
	@Autowired
	@Qualifier("foo")
	private Validator foo;

	@Autowired
	@Qualifier("bar")
	private Validator bar;

	public void sayHello() {
		System.out.print("Qualified foo: ");
		foo.something();
		System.out.print("Qualified bar: ");
		bar.something();
	}
}
