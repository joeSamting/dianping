package com.dp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author Su
 * @Description RabbitMQ配置信息
 * @Date 2022/7/20 20:23
 **/
@Configuration
public class RabbitmqConfig {

    /**
     * 绑定健
     */
    private final String QUEUE_NAME = "rabbitmq.orders";

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue myQueue() {
        return new Queue(QUEUE_NAME,true);
    }
}
