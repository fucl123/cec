package com.supply.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import com.supply.core.ClientException;
import com.supply.custom.CustomSign;
import com.supply.domain.ReceiveDto;
import com.supply.utils.AesUtils;
import com.supply.utils.RSAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

/**
 * @author fcl
 * @version v1.0.0
 * @date 2019/10/21
 */

public class MqReceiver implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MqReceiver.class);
    private static final String HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";

    /**
     * 单机全局限流器(限制QPS为100)
     */
    private RateLimiter rateLimiter;

    @Value("${qps}")
    private int qps;

    @Value("${USBKey}")
    private String USBKey;

    @Value("${company_code}")
    private String companyCode;

    @Value("${public_key}")
    private String publicKey;

    @Value("${private_key}")
    private String privateKey;

    @Value("${aes_key}")
    private String aesKey;
    private CustomSign customSign;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${sendQueue}")
    private String sendQueue;

    @PostConstruct
    public void init() {
        try {
            customSign = new CustomSign(USBKey);
        } catch (ClientException e) {
            LOGGER.error("初始化失败！", e);
        }
        rateLimiter = RateLimiter.create(qps);
        LOGGER.info("初始化成功！");
    }

    @Override
    public void onMessage(Message message) {
        String xml = "";
        String data=null;
        String status = "0000";
        String body = new String(message.getBody());
        LOGGER.info(body);
        ReceiveDto receiveDto=null;
        String sendCompanyCode = null;
        String xmlId=null;
        try {
            receiveDto = JSONObject.parseObject(body, ReceiveDto.class);
            sendCompanyCode=receiveDto.getCompanyCode();
            xmlId=receiveDto.getXmlId();
            xml = RSAUtil.decrypt(receiveDto.getData(), RSAUtil.getPrivateKey(privateKey));
            //如果没有取到锁，线程等待100毫秒
            while (!rateLimiter.tryAcquire()) {
                LOGGER.info("rate limiter qps={}!", qps);
                Thread.sleep(100);
            }
            // 企业备案编码相同的处理，不相同的不处理
            if (companyCode.equals(sendCompanyCode)) {
                if (xml.startsWith("\"")) {
                    xml = xml.trim();
                    xml = xml.substring(1, xml.length() - 1);
                }
                String signXml = HEAD + customSign.doSign(xml);
                data= RSAUtil.encrypt(signXml, RSAUtil.getPublicKey(publicKey));
                LOGGER.info("xml:{}", signXml);
            } else {
                LOGGER.info("sign xml copCode{}, customs code{} is not the same!",receiveDto.getCompanyCode(), companyCode);
            }

        }
        catch (Exception e) {
            try {
                data= RSAUtil.encrypt("加签失败", RSAUtil.getPublicKey(publicKey));
            } catch (Exception e1) {
                LOGGER.error("sign fail, original xml：{}，exception message：{}", xml, e);
            }
            status="1111";
            LOGGER.error("sign fail, original xml：{}，exception message：{}", xml, e);
        }
        JSONObject result=new JSONObject();
        result.put("companyCode",sendCompanyCode);
        result.put("status",status);
        result.put("xmlId",xmlId);
        result.put("data",data);
        rabbitTemplate.convertAndSend(sendQueue, result.toJSONString());
    }

}