package com.emc.plants.messaging;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.emc.plants.messaging.publisher.PaymentInfoPublisher;

public class Main {

	public static void main(String[] args) {
		
		//ApplicationContext context1 = new AnnotationConfigApplicationContext(RabbitClientConfiguration.class);
		ApplicationContext context1 = new ClassPathXmlApplicationContext("app-context.xml");
		System.out.println("RabbitTemplate :: "  + context1.getBean("rabbitTemplate"));
		PaymentInfoPublisher pInfoPublisher = (PaymentInfoPublisher)context1.getBean("paymentInfoPublisher");
		
		pInfoPublisher.publishMessage("Hi.. This is a test message!");
	} 
}
