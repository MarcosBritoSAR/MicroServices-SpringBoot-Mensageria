package com.brito.notification.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;


@Configuration
public class RabbitMqConfig {

    @Value("${queue.name}")
    private String queue;

    @Value("${mq.routes.mail}")
    private String mailRoute;

    @Bean
    public DirectExchange directMail() {
        return new DirectExchange("direct.mail");
    }

    @Bean
    public Queue mailQueue() {
        return new Queue(queue, true);
    }

    @Bean
    public Binding mailBinding(DirectExchange directMail, Queue mailQueue) {
        return bind(mailQueue).to(directMail).with(mailRoute);
    }

}