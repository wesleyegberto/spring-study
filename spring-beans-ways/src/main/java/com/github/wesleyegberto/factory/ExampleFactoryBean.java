package com.github.wesleyegberto.factory;

import org.springframework.beans.factory.config.AbstractFactoryBean;

public class ExampleFactoryBean extends AbstractFactoryBean<BeanCreatedByFactory> {
	// This method determines the type of the bean for autowiring purposes
	@Override
	public Class<?> getObjectType() {
		return BeanCreatedByFactory.class;
	}

	// to change the scope to Prototype (one instance per injection)
	// has no control over its lifecycle after it is returned by this 
	@Override
	public boolean isSingleton() {
		return false;
	}

	// this factory method produces the actual bean
	@Override
	protected BeanCreatedByFactory createInstance() throws Exception {
		// The thing you return can be defined dynamically,
		// that is read from a file, database, network or just
		// simply randomly generated if you wish.
		return new BeanCreatedByFactory();
	}
}