package com.emc.plants.web.util;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebUtil {
	
	private static ApplicationContext context = null;
	public static Object getSpringBean(ServletContext sc, String name){
		
        if(context==null){
        	context = WebApplicationContextUtils.getWebApplicationContext(sc);
            //context = new WebApplica("app-context-web.xml","persistence-context.xml");
        }
        return context.getBean(name);
    }

}
