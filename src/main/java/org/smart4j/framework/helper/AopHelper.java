package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;

public final class AopHelper {
	
	
	static {
		try {
			Map<Class<?>, Set<Class<?>>>  proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>>  targetMap = createTargetMap(proxyMap);
			for(Map.Entry<Class<?>, List<Proxy>> targetEntry:targetMap.entrySet()) {
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				
				Proxy proxy = ProxyManager.createProxy(targetClass, proxyList);
				BeanHelper.setBean(targetClass, proxy);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 封装具有@Aspect注解中设置的注解类
	 * @param aspect
	 * @return
	 * @throws Exception
	 */
	public static final Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception{
		Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
		Class<? extends Annotation> annotation = aspect.value();
		if(annotation != null && !annotation.equals(Aspect.class)) {
			targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
		}
		return targetClassSet;
	}
	
	
	/**
	 * 获取代理类与目标类集合的映射关系
	 * @return
	 * @throws Exception
	 */
	public static final Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception{
		Map<Class<?>,Set<Class<?>>> proxyMap = new HashMap<Class<?>,Set<Class<?>>>();
		Set<Class<?>> classSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
		for(Class<?> proxyClass:classSet) {
			if(proxyClass.isAnnotationPresent(Aspect.class)) {
				Aspect aspect = proxyClass.getAnnotation(Aspect.class);
				Set<Class<?>> annotationClassSet = createTargetClassSet(aspect);
				proxyMap.put(proxyClass, annotationClassSet);
			}
		}
		return proxyMap;
	}
	
	/**
	 * 根据createProxyMap()获取的目标类和代理对象集合之间的映射关系
	 * @param proxyMap
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static final Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws InstantiationException, IllegalAccessException{
		Map<Class<?>,List<Proxy>> targetMap = new HashMap<Class<?>,List<Proxy>>();
		for(Map.Entry<Class<?>, Set<Class<?>>> proxyEntry:proxyMap.entrySet()) {
			Class<?> proxyClass = proxyEntry.getKey();
			Set<Class<?>> targetClassSet = proxyEntry.getValue();
			for(Class<?> targetClass : targetClassSet) {
				Proxy proxy = (Proxy) proxyClass.newInstance();
				if(targetMap.containsKey(targetClass)) {
					targetMap.get(targetClass).add(proxy);
				}else {
					List<Proxy> proxyList = new ArrayList<Proxy>();
					proxyList.add(proxy);
					targetMap.put(targetClass, proxyList);
				}
			}
		}
		return targetMap;
	}
}
