package com.microservices.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.notification.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DepositMessageHandler {

    private final JavaMailSender javaMailSender;

    @Autowired
    public DepositMessageHandler(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receive(Message message) throws JsonProcessingException {
        System.out.println(message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDto depositResponse = objectMapper.readValue(jsonBody, DepositResponseDto.class);
        System.out.println(depositResponse);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(depositResponse.getEmail());
        mailMessage.setFrom("xyz@mail.ru");


        mailMessage.setSubject("Deposit");
        mailMessage.setText("Make deposit, sum: " + depositResponse.getAmount());

        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
