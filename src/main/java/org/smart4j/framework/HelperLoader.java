package org.smart4j.framework;

import org.smart4j.framework.helper.AopHelper;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ClassHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.helper.IOCHelper;
import org.smart4j.framework.util.ClassUtil;

public final class HelperLoader {

	public static void init(){
		Class<?>[] classList={
				BeanHelper.class,
				ClassHelper.class,
				ConfigHelper.class,
				ControllerHelper.class,
				AopHelper.class,//AopHelper要在IOCHelper之前加载,依赖注入才会获取到代理对象
				IOCHelper.class};
		
		for(Class<?> cls:classList){
			ClassUtil.loadClass(cls.getName(), false);
		}
	}
}
