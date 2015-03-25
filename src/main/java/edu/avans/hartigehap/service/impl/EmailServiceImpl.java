package edu.avans.hartigehap.service.impl;

import edu.avans.hartigehap.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Created by Mark on 8-3-2015.
 */
public class EmailServiceImpl implements NotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private MailSender mailSender;

    public void setMailSender (MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public boolean sendNotification (String from, String to, String subject, String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(msg);

        try {
            mailSender.send(message);
        } catch (Exception e) {
            LOGGER.warn("Error occurred in email service provider",e);
            return false;
        }
        return true;
    }
}
