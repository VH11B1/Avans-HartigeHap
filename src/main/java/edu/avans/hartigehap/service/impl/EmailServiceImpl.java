package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Mark on 8-3-2015.
 */
public class EmailServiceImpl implements NotificationService {

    MailSender mailSender;

    public void setMailSender (MailSender  mailSender){
        this.mailSender = mailSender;
    }

    public boolean sendNotification(String from, String to, String subject, String msg){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);

        try {
            mailSender.send(message);
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
