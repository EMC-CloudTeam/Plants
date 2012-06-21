package com.emc.plants.messaging.config;





import org.apache.log4j.Logger;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.emc.plants.messaging.listener.MsgConsumer;
import com.emc.plants.messaging.publisher.PaymentInfoPublisher;

@Configuration
public class RabbitClientConfiguration {
	
	public static Logger logger = Logger.getLogger(MsgConsumer.class);
	RabbitClientConfiguration(){
		logger.info("RabbitClientConfiguration!!");
	}
	

	protected final String payProcQueueName = "PAYMENT_QUEUE";

	@Bean
	public ConnectionFactory connectionFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		return connectionFactory;
	}
	
	@Bean
	public PaymentInfoPublisher paymentInfoPublisher() {
		PaymentInfoPublisher gateway = new PaymentInfoPublisher();
		gateway.setRabbitTemplate(rabbitTemplate());
		return gateway;
	}
	
	@Bean
	public AmqpAdmin amqpAdmin() {
		return new RabbitAdmin(connectionFactory());
	}

	@Bean
	public RabbitTemplate rabbitTemplate() {
		//System.out.println( " Invoked rabbitTemplate!");
		RabbitTemplate template = new RabbitTemplate(connectionFactory());
		
		//The routing key is set to the name of the queue by the broker for the default exchange.
		template.setRoutingKey(this.payProcQueueName);
		//Where we will synchronously receive messages from
		template.setQueue(this.payProcQueueName);
		return template;
	}

	
	

	/*
	@Bean 
	public Binding binding() {
		return declare(new Binding(helloWorldQueue(), defaultDirectExchange()));
	}*/
	
	/*	
	@Bean
	public TopicExchange helloExchange() {
		return declare(new TopicExchange("hello.world.exchange"));
	}*/
	
	/*
	public Queue declareUniqueQueue(String namePrefix) {
		Queue queue = new Queue(namePrefix + "-" + UUID.randomUUID());
		rabbitAdminTemplate().declareQueue(queue);
		return queue;
	}
	
	// if the default exchange isn't configured to your liking....
	@Bean Binding declareP2PBinding(Queue queue, DirectExchange exchange) {
		return declare(new Binding(queue, exchange, queue.getName()));
	}
	
	@Bean Binding declarePubSubBinding(String queuePrefix, FanoutExchange exchange) {
		return declare(new Binding(declareUniqueQueue(queuePrefix), exchange));
	}
	
	@Bean Binding declarePubSubBinding(UniqueQueue uniqueQueue, TopicExchange exchange) {
		return declare(new Binding(uniqueQueue, exchange));
	}
	
	@Bean Binding declarePubSubBinding(String queuePrefix, TopicExchange exchange, String routingKey) {
		return declare(new Binding(declareUniqueQueue(queuePrefix), exchange, routingKey));
	}*/

}
