package org.smart4j.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang3.StringUtils;
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
			//将 initialize 设定为 false，这样在加载类时并不会立即运行静态区块，而会在使用类建立对象时才运行静态区块
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
						//路径空格会被“%20”代替
						String packagePath = url.getPath().replaceAll("%20", "");
						addClass(classSet,packagePath,packageName);
					}else if("jar".equals(protocol)) {
						JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
						if(jarURLConnection != null) {
							JarFile jarFile = jarURLConnection.getJarFile();
							if(jarFile != null) {
								Enumeration<JarEntry> jarEntries = jarFile.entries();
								while(jarEntries.hasMoreElements()) {
									JarEntry jarEntry = jarEntries.nextElement();
									String jarEntryName = jarEntry.getName();
									if(jarEntryName != null && jarEntryName.endsWith(".class")) {
										String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replace("/", ".");
										doAddClass(classSet,className);
									}
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classSet;
	}
	
	private static void addClass(Set<Class<?>> classSet,String packagePath,String packageName){
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class"))||file.isDirectory();
			}
			
		});
		
		for(File file:files) {
			String fileName = file.getName();
			if(file.isFile()) {
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if(StringUtils.isNotBlank(packageName)) {
					className=packageName+"."+className;
				}
				doAddClass(classSet,className);
			}else {
				String subPackagePath=fileName;
				if(StringUtils.isNotBlank(subPackagePath)) {
					subPackagePath=packagePath+"/"+subPackagePath;
				}
				String subPackageName=fileName;
				if(StringUtils.isNotBlank(subPackageName)) {
					subPackageName=packageName+"."+subPackageName;
				}
				addClass(classSet,subPackagePath,subPackageName);
			}
		}
	}

	private static void doAddClass(Set<Class<?>> classSet,String className){
		Class<?> cls = loadClass(className,false);
		classSet.add(cls);
	}
}
