package com.github.wesleyegberto;

import com.github.wesleyegberto.factory.BeanCreatedByFactory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        //initializing the Application Context once per application.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        
        //bean registered by annotation
        BeanDeclaredByAnnotation beanDeclaredByAnnotation = applicationContext.getBean(BeanDeclaredByAnnotation.class);
        beanDeclaredByAnnotation.sayHello();

        //bean registered by Java configuration file
        BeanDeclaredInAppConfig beanDeclaredInAppConfig = applicationContext.getBean(BeanDeclaredInAppConfig.class);
        beanDeclaredInAppConfig.sayHello();

        //showcasing constructor injection
        BeanConstructorInjection beanConstructorInjection = applicationContext.getBean(BeanConstructorInjection.class);
        beanConstructorInjection.sayHello();

        //showcasing property injection
        BeanPropertyInjection beanPropertyInjection = applicationContext.getBean(BeanPropertyInjection.class);
        beanPropertyInjection.sayHello();

        //showcasing PreConstruct / PostDestroy hooks
        BeanPostConstructPreDestroy beanPostConstructPreDestroy = applicationContext.getBean(BeanPostConstructPreDestroy.class);
        beanPostConstructPreDestroy.sayHello();

        BeanCreatedByFactory beanCreatedByFactory = applicationContext.getBean(BeanCreatedByFactory.class);
        beanCreatedByFactory.sayHello();

        FactoryBean<BeanCreatedByFactory> factory = (FactoryBean<BeanCreatedByFactory>) applicationContext.getBean("&fromFactory");
        System.out.println("Factory: " + factory);

        BeanInjectingWithQualifiers beanInjectingWithQualifiers = applicationContext.getBean(BeanInjectingWithQualifiers.class);
        beanInjectingWithQualifiers.sayHello();

        String connectionString = applicationContext.getBean("connection-string", String.class);
        System.out.println("Bean for this ENV: " + connectionString);
    }
}
