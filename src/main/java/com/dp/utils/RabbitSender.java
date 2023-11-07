package com.dp.utils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.Resource;

public class RabbitSender {

    @Resource
    public RabbitTemplate rabbitTemplate;


}
