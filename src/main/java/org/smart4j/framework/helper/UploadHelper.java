package org.smart4j.framework.helper;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public final class UploadHelper {

	/**
	 * apache commons-fileupload 提供的servlet上传对象
	 */
	public static ServletFileUpload servletFileUpload;
	
	
	public static void init(ServletContext servletContext){
		File reposity = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD,reposity));
		int uploadLimit = ConfigHelper.getAppUploadLimit();
		if(uploadLimit != 0){
			servletFileUpload.setFileSizeMax(uploadLimit * 1024*1024);
		}
	}
	
	/**
	 * 判断请求类型是否是multipart类型
	 * @param request
	 * @return
	 */
	public static boolean isMultipart(HttpServletRequest request){
		return ServletFileUpload.isMultipartContent(request);
	}
	
}
