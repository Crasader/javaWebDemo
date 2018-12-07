package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AspectProxy implements Proxy{

	private static Logger logger = LoggerFactory.getLogger(AspectProxy.class);
	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		Class<?> cls = proxyChain.getClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		
		begin();
		try {
			if(intercept(cls,method,params)) {
				before(cls,method,params);
				result = proxyChain.doProxyChain();
				after(cls,method,params,result);
			}else {
				result = proxyChain.doProxyChain();
			}
		}catch(Exception e) {
			logger.error("error");
			error(cls,method,params,result,e);
		}finally {
			end();
		}
		return result;
	}
	
	public void begin() {
		
	}

	public boolean intercept(Class<?> cls,Method method,Object[] params) {
		return true;
	}
	
	public abstract void before(Class<?> cls,Method method,Object[] params) throws Throwable;
	
	public abstract void after(Class<?> cls,Method method,Object[] params,Object result) throws Throwable;

	public  void error(Class<?> cls,Method method,Object[] params,Object result,Throwable throwable) {
		
	}

	public void end() {
		
	}
	
}
