package com.example.demo.Service.Consumer;

import com.example.demo.Models.DTO.MessageDTO;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(MessageDTO messageDTO){
        System.out.println("We are getting a mail request");;
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = "Booking Confirmed for the event "+messageDTO.getAllDetailsDTO().getEventDTO().getName()+" at "+messageDTO.getAllDetailsDTO().getLocationDetailsDTO().getVenueEntity().getName();
        String body = messageDTO.mailBody();
        message.setTo(messageDTO.getEmail());
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        System.out.println("We sent  mail");
    }
}
