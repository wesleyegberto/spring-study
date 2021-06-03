package com.github.wesleyegberto.qualifiers;

import org.springframework.stereotype.Component;

@Component("foo")
public class FooValidator implements Validator {
    public void something() {
        System.out.println("Foo validator!");
    }
}