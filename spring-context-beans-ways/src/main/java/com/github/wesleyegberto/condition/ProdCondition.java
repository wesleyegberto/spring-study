package com.github.wesleyegberto.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Condition to determine whether we are running in PROD or not.
 */
public class ProdCondition implements Condition {
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return "PROD".equals(context.getEnvironment().getProperty("ENV"));
	}
}