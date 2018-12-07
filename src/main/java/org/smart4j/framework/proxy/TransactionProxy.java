package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.smart4j.framework.annotation.TransAction;
import org.smart4j.framework.helper.DataBaseHelper;

public class TransactionProxy implements Proxy {
	private static final ThreadLocal<Boolean> FLAG_HOLDER =  new ThreadLocal<Boolean>(){

		@Override
		protected Boolean initialValue() {
			return false;
		}
		
	};

	public Object doProxy(ProxyChain proxyChain) throws Throwable {
		
		Object result = null;
		boolean flag = FLAG_HOLDER.get();
		Method method = proxyChain.getTargetMethod();
		if(!flag && method.isAnnotationPresent(TransAction.class)) {
			FLAG_HOLDER.set(true);
			try {
				DataBaseHelper.beginTransaction();
				result = proxyChain.doProxyChain();
				DataBaseHelper.commitTransaction();
				
			}catch(Exception e) {
				DataBaseHelper.rollbackTransaction();
				throw e;
			}finally {
				FLAG_HOLDER.remove();
			}
		}else {
			result = proxyChain.doProxyChain();
		}
		return result;
	}

	
}
