package com.emc.plants.messaging.listener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.emc.plants.persistence.PaymentProcessMessage;
import com.emc.plants.service.impl.PaymentProcessBean;
import com.emc.plants.utils.Util;

@Component
public class MsgConsumer{
	
	private static ApplicationContext context = null;
	
	public static Logger logger = Logger.getLogger(MsgConsumer.class);

	public MsgConsumer() {
		// TODO Auto-generated constructor stub
		logger.info("Listener constructed!!");
	}
	
	
	public void handleMessage(String str){
		logger.info("Received Message :: "+str);
		
		PaymentProcessMessage paymntProcMsg = new PaymentProcessMessage();
		paymntProcMsg.setPaymntProcMsg(str);
		PaymentProcessBean paymntProcBean = (PaymentProcessBean)getSpringBean("paymntProcess");
		
		paymntProcBean.persistPaymentInfoMsg(paymntProcMsg);
		
		logger.info("Persist Invoked!");
	}
	
	public static Object getSpringBean(String name){
        if(context==null){
            context = new ClassPathXmlApplicationContext("app-context-service.xml","persistence-context.xml");
        }
        return context.getBean(name);
    }
	
}
