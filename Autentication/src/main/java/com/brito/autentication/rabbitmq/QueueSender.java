package com.brito.autentication.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class QueueSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.name}")
    private String queueName;


    public boolean send(String message) {
        try {
            log.info("Enviando mensagem. Conteudo: {}. Fila: {}", message, queueName);
            rabbitTemplate.convertAndSend(Objects.requireNonNull(queueName), message);
            return true;

        }catch (Exception e){
            log.error("Erro ao enviar mensagem", e);
            return false;
        }
    }
}
