package com.brito.notification;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    @RabbitListener(queues = {"${queue.name}"})
    public void receiver(@Payload String message) {
        System.out.println("Message " + message);
    }
}
