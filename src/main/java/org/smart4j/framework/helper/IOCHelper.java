package org.smart4j.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ReflectionUtil;

/**
 * 依赖助手类
 * @author
 *
 */
public final class IOCHelper {
	
	static {
		//获取所有的bean类和bean实例的映射关系
		Map<Class<?>,Object> beanMap = BeanHelper.getBeanMap();
		if(beanMap != null) {
			for(Map.Entry<Class<?>, Object> beanEntry:beanMap.entrySet()) {
				Class<?> beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();
				Field[] fields = beanClass.getDeclaredFields();
				if(fields != null && fields.length>0) {
					for(Field field:fields) {
						if(field.isAnnotationPresent(Inject.class)) {
							Class<?> fieldClass = field.getType();
							Object beanFieldInstance = beanMap.get(fieldClass);
							if(beanFieldInstance != null) {
								ReflectionUtil.setField(beanInstance, field, beanFieldInstance);
							}
						}
					}
				}
			}
		}
	}

}
