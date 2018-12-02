package org.smart4j.framework.util;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ClassUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
	
	/**
	 * 获取类加载器
	 * @return
	 */
	public static ClassLoader getClassLoader(){
		return Thread.currentThread().getContextClassLoader();
	}
	
	/**
	 * 加载类
	 * @param className
	 * @param isInitialized
	 * @return
	 */
	public static Class<?> loadClass(String className,boolean isInitialized){
		Class<?> cls=null;
		try {
			cls = Class.forName(className, isInitialized, getClassLoader());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return cls;
	}
	
	public static Set<Class<?>> getClassSet(String packageName){
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		try {
			//这种传统接口已被迭代器取代，虽然Enumeration 还未被遗弃，但在现代代码中已经被很少使用了。尽管如此，它还是使用在诸如Vector和Properties这些传统类所定义的方法中，除此之外，还用在一些API类，并且在应用程序中也广泛被使用
			Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
			while(urls.hasMoreElements()){
				URL url = urls.nextElement();
				if(url != null){
					String protocol = url.getProtocol();
					if("file".equals(protocol)){
						String packagePath = url.getPath().replaceAll("%20", "");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void addClass(Set<Class<?>> classSet,String packagePath,String packageName){
		
	}

	private static void doAddClass(Set<Class<?>> classSet,String className){
		Class<?> cls = loadClass(className,false);
		classSet.add(cls);
	}
}
