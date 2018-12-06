package org.smart4j.framework.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//只能用在类上
@Retention(RetentionPolicy.RUNTIME)//运行时生效
public @interface Aspect {

	/**
	 * 注解
	 * @return
	 */
	Class<? extends Annotation> value();
}
