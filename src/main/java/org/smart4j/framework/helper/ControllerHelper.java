package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;


public final class ControllerHelper {

	/**
	 * 用于存放请求与处理器的映射关系
	 */
	private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request,Handler>();
	
	static {
		Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
		if(controllerClassSet != null && controllerClassSet.size() >0) {
			for(Class<?> controllerClass:controllerClassSet) {
				Method[] methods = controllerClass.getDeclaredMethods();
				if(methods != null && methods.length >0) {
					for(Method method:methods) {
						if(method.isAnnotationPresent((Class<? extends Annotation>) Action.class)) {
							//从@Action中获取url映射规则
							Action action = method.getAnnotation(Action.class);
							String mapping = action.value();
							if(mapping.matches("\\w+:/\\w*")) {
								
							}
						}
					}
				}
			}
		}
	}
}
