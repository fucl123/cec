package com.supply.core;

import com.supply.service.MqReceiver;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/21
 */

@Configuration
public class RabbitMqConfig
{
	@Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.port}")
	private String port;

	@Value("${spring.rabbitmq.username}")
	private String userName;

	@Value("${spring.rabbitmq.password}")
	private String password;

	@Value("${receiveQueue}")
	private String receiveQueue;

	@Value("${sendQueue}")
	private  String sendQueue;

	@Value("${virtualHost}")
	private String virtualHost;

//	@Autowired
//	MqReceiver receiver;

	@Bean
	public ConnectionFactory connectionFactory()
	{
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(host + ":" + port);
		connectionFactory.setUsername(userName);
		connectionFactory.setPassword(password);
		connectionFactory.setVirtualHost(virtualHost);
        //必须要设置
		connectionFactory.setPublisherConfirms(true);
		return connectionFactory;
	}
    @Bean
	public  MqReceiver mqReceiver(){
	    return new MqReceiver();
    }
	@Bean
	public RabbitTemplate rabbitTemplate()
	{
        return new RabbitTemplate(connectionFactory());
	}

	@Bean
	public SimpleMessageListenerContainer simpleMessageListenerContainer()
	{
		SimpleMessageListenerContainer simpleMessageListenerContainer =
				new SimpleMessageListenerContainer(connectionFactory());

		//simpleMessageListenerContainer.setQueues(new Queue(receiveQueue));
//		simpleMessageListenerContainer.setQueues(new Queue("44269619NY"));
//		simpleMessageListenerContainer.setQueues(new Queue("55369628NY"));
//		simpleMessageListenerContainer.setQueues(new Queue("33169609NY"));
		simpleMessageListenerContainer.setExposeListenerChannel(true);
		simpleMessageListenerContainer.setMaxConcurrentConsumers(1);
        simpleMessageListenerContainer.setPrefetchCount(1);
		//串行加签，解决并发加签usb初始化不成功
		simpleMessageListenerContainer.setConcurrentConsumers(1);
        //设置确认模式手工确认
		simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
		simpleMessageListenerContainer.setupMessageListener(mqReceiver());
		return simpleMessageListenerContainer;
	}

}
