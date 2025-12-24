package com.example.demo.Service.Consumer;

import com.example.demo.Models.DTO.MessageDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumerService {

    private final EmailService emailService;

    public MessageConsumerService(EmailService emailService){
        this.emailService = emailService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consumer(MessageDTO messageDTO) throws Exception {
        try {
            emailService.sendEmail(messageDTO);
        } catch (Exception e) {
            System.out.println(messageDTO);
            System.out.println(e.getMessage());
        }
    }
}
