package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.util.ClassUtil;

import com.sun.xml.internal.ws.wsdl.writer.document.Service;

public final class ClassHelper {

	/**
	 * 定义类集合（用于存放加载的类）
	 */
	private static final Set<Class<?>> CLASS_SET;
	
	static {
		String basePackage = ConfigHelper.getAppBasePackage();
		CLASS_SET = ClassUtil.getClassSet(basePackage);
	}
	
	/**
	 * 获取包下所有类
	 * @return
	 */
	public static Set<Class<?>> getClassSet(){
		return CLASS_SET;
	}
	
	/**
	 * 获取@service注解类
	 * @return
	 */
	public static Set<Class<?>> getServiceClassSet(){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls:CLASS_SET) {
			if(cls.isAnnotationPresent((Class<? extends Annotation>) Service.class)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取所有@controller注解类
	 * @return
	 */
	public static Set<Class<?>> getControllerClassSet(){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		for(Class<?> cls:CLASS_SET) {
			if(cls.isAnnotationPresent(Controller.class)) {
				classSet.add(cls);
			}
		}
		return classSet;
	}
	
	/**
	 * 获取所有bean类
	 * @return
	 */
	public static Set<Class<?>> getBeanClassSet(){
		Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
		beanClassSet.addAll(getServiceClassSet());
		beanClassSet.addAll(getControllerClassSet());
		return beanClassSet;
	}
}
