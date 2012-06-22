package com.emc.plants.messaging.publisher;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.support.RabbitGatewaySupport;

/**
 * 
 * @author maddus
 * 
 */
public class PaymentInfoPublisher extends RabbitGatewaySupport {
	
	//private RabbitTemplate rabbitTemplate;

	private static Logger logger = Logger.getLogger("PaymentInfoPublisher");

	
	public void publishMessage(String msg) {

		logger.info("RabbitTemplate in PublishMessage :: " +getRabbitTemplate());
		
		logger.info(" Message going to publish :: " +msg);
		
		if(getRabbitTemplate()!=null){
			getRabbitTemplate().convertAndSend(msg);
		}
		logger.info("Sent: "+msg);
	}

}
