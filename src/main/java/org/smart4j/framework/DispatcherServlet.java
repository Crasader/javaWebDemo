package org.smart4j.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.BeanHelper;
import org.smart4j.framework.helper.ConfigHelper;
import org.smart4j.framework.helper.ControllerHelper;
import org.smart4j.framework.util.CodecUtil;
import org.smart4j.framework.util.JsonUtil;
import org.smart4j.framework.util.ReflectionUtil;
import org.smart4j.framework.util.StreamUtil;

@WebServlet(urlPatterns="/*",loadOnStartup=0)
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取请求方法与请求路径
		String requestMethod = request.getMethod().toLowerCase();
		String requestPath = request.getPathInfo();
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		if(handler != null) {
			Class<?> controllerClass = handler.getControllerClass();
			Object controllerBean = BeanHelper.getBean(controllerClass);
			Map<String,Object> paramMap = new HashMap<String,Object>();
			//post或者form请求
			Enumeration<String> paramNames = request.getParameterNames();
			while(paramNames.hasMoreElements()) {
				String paramName = paramNames.nextElement();
				String paramValue = request.getParameter(paramName);
				paramMap.put(paramName, paramValue);
			}
			
			String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
			if(StringUtils.isNotBlank(body)) {
				String[] bodyArr = body.split("&");
				if(bodyArr != null && bodyArr.length>0) {
					for(String str:bodyArr) {
						String[] array = str.split("=");
						if(array != null && array.length==2) {
							String paramName=array[0];
							String paramValue=array[1];
							paramMap.put(paramName, paramValue);
						}
					}
				}
			}
			Param param = new Param(paramMap);
			Method actionMethod = handler.getActionMethod();
			Object result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
			if(result instanceof View) {
				View view = (View)result;
				String path = view.getPath();
				if(path.startsWith("/")) {
					response.sendRedirect(request.getContextPath()+path);
				}else {
					Map<String,Object> model = view.getModel();
					for(Map.Entry<String, Object> entry:model.entrySet()) {
						request.setAttribute(entry.getKey(), entry.getValue());
					}
					request.getRequestDispatcher(ConfigHelper.getAppJspPath()+path).forward(request, response);
				}
			}else if(result instanceof Data) {
				Data data=(Data)result;
				Object model = data.getModel();
				if(model != null) {
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");
					PrintWriter  printWriter  = response.getWriter();
					String json = JsonUtil.toJson(model);
					printWriter.write(json);
					printWriter.flush();
					printWriter.close();
				}
			}
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		//初始化相关helper类
		HelperLoader.init();
		//获取ServletContext对象（用于获取servlet）
		ServletContext servletContext= config.getServletContext();
		//注册处理jsp的servlet
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath()+"*");
		//静态资源servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath()+"*");
	}

	
	
}
