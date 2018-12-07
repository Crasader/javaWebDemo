package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy {

	private static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
	
	private long begin;
	
	@Override
	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
      logger.debug("----begin----");
      logger.debug(cls.getName());
      logger.debug(method.getName());
	  begin = System.currentTimeMillis();
	}

	@Override
	public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {

		logger.debug("用时:"+(System.currentTimeMillis()-begin));
		logger.debug("---end---");
	}

}
