package com.github.wesleyegberto;

import com.github.wesleyegberto.condition.DevCondition;
import com.github.wesleyegberto.condition.ProdCondition;
import com.github.wesleyegberto.factory.BeanCreatedByFactory;
import com.github.wesleyegberto.factory.ExampleFactoryBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("com.github.wesleyegberto")
@PropertySource("app.properties")
public class AppConfig {
	@Bean
	public BeanDeclaredInAppConfig beanDeclaredInAppConfig() {
		return new BeanDeclaredInAppConfig();
	}

	@Bean
	@Lazy
	public FactoryBean<BeanCreatedByFactory> fromFactory() {
		return new ExampleFactoryBean();
	}

	@Bean(name = "connection-string")
	@Conditional(DevCondition.class)
	public String getConnectionStringToDev() {
		return "jdbc://devdb";
	}

	@Bean(name = "connection-string")
	@Conditional(ProdCondition.class)
	public String getConnectionStringToProd() {
		return "jdbc://proddb";
	}
	
	@Bean
	public PropertySourcesPlaceholderConfigurer gPropertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
