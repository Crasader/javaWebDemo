package org.smart4j.framework.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

public class ProxyChain {

	private final Class<?> targetClass;
	
	private final Object targetObjet;
	
	private final Method targetMethod;
	
	private final MethodProxy methodProxy;
	
	private final Object[] methodParams;
	
	private List<Proxy> proxyList = new ArrayList<Proxy>(); 
	
	private int proxyIndex=0;


	public ProxyChain(Class<?> targetClass, Object targetObjet, Method targetMethod, MethodProxy methodProxy,
			Object[] methodParams, List<Proxy> proxyList) {
		super();
		this.targetClass = targetClass;
		this.targetObjet = targetObjet;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
		this.proxyList = proxyList;
	}

	public Object getTargetObjet() {
		return targetObjet;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}


	public Object[] getMethodParams() {
		return methodParams;
	}

	public MethodProxy getMethodProxy() {
		return methodProxy;
	}




	public Class<?> getTargetClass() {
		return targetClass;
	}
	
	//Throwable是error,exception基类
	public Object doProxyChain()  throws Throwable{
		Object methodResult=null;
		
		if(proxyIndex < proxyList.size()) {
			methodResult = proxyList.get(proxyIndex++).doProxy(this);
		}else {
			methodResult = methodProxy.invokeSuper(targetObjet, methodParams);
		}
		return methodResult;
		
	}
	
}
