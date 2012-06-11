package com.emc.plants.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emc.plants.pojo.beans.CustomerInfo;
import com.emc.plants.service.interfaces.Login;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ApplicationContext context = new ClassPathXmlApplicationContext(
        		"app-context.xml");
/*        ApplicationContext context = new Annotation(
				"app-context.xml");
*/
		System.out.println("Spring Context :: " + context.getBean("loginBean"));

		Login loginBean = (Login) context.getBean("loginBean");

		CustomerInfo cInfo = (CustomerInfo)loginBean.getCustomerInfo("123");
		
		System.out.println("First Name :: "+cInfo.getFirstName());
    }
}
