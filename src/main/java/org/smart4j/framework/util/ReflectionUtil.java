package org.smart4j.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReflectionUtil {

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);
	
	/**
	 * 获取实例
	 * @param cls
	 * @return
	 */
	public static Object newInstance(Class<?> cls) {
		Object instance=null;
			try {
				instance=cls.newInstance();
			} catch (Exception e) {
				logger.error("failure:{}",e);
				throw new RuntimeException();
			}
			return instance;
	}
	
	/**
	 * 调用方法
	 * @param obj
	 * @param method
	 * @param args
	 * @return
	 */
	public static Object invokeMethod(Object obj,Method method,Object... args) {
		
		Object result=null;
		try {
			method.setAccessible(true);
			result = method.invoke(obj, args);
		} catch (Exception e) {
			logger.error("failure:{}",e);
			throw new RuntimeException();
		}
		return result;
	}
	
	/**
	 * 设置成员变量值
	 * @param obj
	 * @param field
	 * @param value
	 */
	public static void setField(Object obj,Field field,Object value) {
		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			logger.error("failure:{}",e);
			throw new RuntimeException();
		}
	}
}
