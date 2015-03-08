package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Mark on 8-3-2015.
 */
public class EmailServiceImpl implements NotificationService {

    public boolean sendNotification(String receiver, String message){
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername("ivh11b1@gmail.com");
        sender.setPassword("@welkom1");

        Properties props = new Properties();
        props.setProperty("mail.smtp.ssl.trust","smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable","true");
        props.setProperty("mail.smtp.auth","true");

        sender.setJavaMailProperties(props);

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setTo(receiver);
            helper.setText(message);
            sender.send(mimeMessage);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
