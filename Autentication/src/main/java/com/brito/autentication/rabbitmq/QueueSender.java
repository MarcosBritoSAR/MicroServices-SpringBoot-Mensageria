package com.brito.autentication.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class QueueSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment environment;

    public boolean send(String message) {
        try {
            log.info(String.format("Enviando mensagem. Conteudo: %s. Fila: %s", message, environment.getProperty("RABBITMQ_NAME")));
            rabbitTemplate.convertAndSend(Objects.requireNonNull(environment.getProperty("RABBITMQ_NAME")), message);
            return true;

        }catch (Exception e){
            log.error("Erro ao enviar mensagem", e);
            return false;
        }
    }
}
