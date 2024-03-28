package com.minis.web.servlet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minis.beans.BeansException;
import com.minis.web.AnnotationConfigWebApplicationContext;
import com.minis.web.XmlScanComponentHelper;
import com.minis.web.context.WebApplicationContext;


/**
 * Servlet implementation class DispatcherServlet
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";
	private WebApplicationContext webApplicationContext;
	private WebApplicationContext parentApplicationContext;

	private String sContextConfigLocation;
	private List<String> packageNames = new ArrayList<>();
	private Map<String,Object> controllerObjs = new HashMap<>();
	private List<String> controllerNames = new ArrayList<>();
	private Map<String,Class<?>> controllerClasses = new HashMap<>();

	private HandlerMapping handlerMapping;
	private HandlerAdapter handlerAdapter;

	public DispatcherServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);


		this.parentApplicationContext =
				(WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		sContextConfigLocation = config.getInitParameter("contextConfigLocation");

		URL xmlPath = null;
		try {
			xmlPath = this.getServletContext().getResource(sContextConfigLocation);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);

		this.webApplicationContext = new AnnotationConfigWebApplicationContext(sContextConfigLocation,this.parentApplicationContext);


		refresh();

	}

	protected void refresh() {
		initController();

//		initHandlerMappings(this.webApplicationContext);
//		initHandlerAdapters(this.webApplicationContext);


		/**
		 * 这两个容器一个是配置文件applicationContext.xml解析bean容器，一个是包扫描controller的bean容器。
		 * 而webBindingInitializer这个bean的定义在applicationContext.xml配置文件中，所以传入webApplicationContext
		 * 这个容器对象是获取不到的。改为parentApplicationContext就可以正确执行下去
		 */
		initHandlerMappings(this.webApplicationContext);
		initHandlerAdapters(this.parentApplicationContext);

//		initViewResolvers(this.webApplicationContext);
	}

	protected void initHandlerMappings(WebApplicationContext wac) {
		this.handlerMapping = new RequestMappingHandlerMapping(wac);

	}
	protected void initHandlerAdapters(WebApplicationContext wac) {
		this.handlerAdapter = new RequestMappingHandlerAdapter(wac);

	}

	protected void initViewResolvers(WebApplicationContext wac) {

	}

	protected void initController() {
		this.controllerNames = Arrays.asList(this.webApplicationContext.getBeanDefinitionNames());
		for (String controllerName : this.controllerNames) {
			try {
				this.controllerClasses.put(controllerName,Class.forName(controllerName));
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			try {
				this.controllerObjs.put(controllerName,this.webApplicationContext.getBean(controllerName));
				System.out.println("controller : "+controllerName);
			} catch (BeansException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);

		try {
			doDispatch(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
		}
	}

	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletRequest processedRequest = request;
		HandlerMethod handlerMethod = null;

		handlerMethod = this.handlerMapping.getHandler(processedRequest);
		if (handlerMethod == null) {
			return;
		}

		HandlerAdapter ha = this.handlerAdapter;

		ha.handle(processedRequest, response, handlerMethod);
	}


}