package com.supply;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class CecApplicationTests {
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Test
	void contextLoads() {

		rabbitTemplate.convertAndSend("3702100002", "123");
	}

}
