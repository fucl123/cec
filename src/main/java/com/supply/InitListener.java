package com.supply;

import com.rabbitmq.client.Channel;
import com.supply.domain.Company;
import com.supply.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Component
public class InitListener implements ApplicationRunner {

    @Autowired
    CompanyService companyService;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    SimpleMessageListenerContainer simpleMessageListenerContainer;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        //读取分中心申请好的企业，循环生成监听器，并启动
        List<Company> list=companyService.selectList(null);

        Channel channelE = connectionFactory.createConnection().createChannel(false);
        for(Company company:list)
        {
            if(!StringUtils.isEmpty(company.getCompanyCode()))
            {
                channelE.queueDeclare(company.getCompanyCode(), true, false, false, null);
                channelE.queueDeclare(company.getCompanyCode()+"_HZ", true, false, false, null);
                simpleMessageListenerContainer.addQueueNames(company.getCompanyCode());
                log.info("companyCode:{}",company.getCompanyCode());
            }
        }


        log.info("系统监听启动完成。开始接收企业报文！");

    }
}
