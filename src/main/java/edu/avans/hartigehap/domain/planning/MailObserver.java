package edu.avans.hartigehap.domain.planning;

import edu.avans.hartigehap.service.NotificationService;
import edu.avans.hartigehap.service.impl.EmailServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Mark on 9-3-2015.
 */
public class MailObserver implements IObserver{
    @Override
    public void update(Employee employee, Employee supervisor, String subject, String message) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-context.xml");
        NotificationService notificationService = (EmailServiceImpl) context.getBean("emailService");
        notificationService.sendNotification("no-reply@hh.nl",supervisor.getEmail(), subject, message);
    }
}