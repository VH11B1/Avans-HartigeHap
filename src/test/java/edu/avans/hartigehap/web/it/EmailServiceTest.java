package edu.avans.hartigehap.web.it;

import edu.avans.hartigehap.service.NotificationService;
import edu.avans.hartigehap.service.impl.EmailServiceImpl;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertTrue;

/**
 * Created by Mark on 9-3-2015.
 */
public class EmailServiceTest extends AbstractTransactionRollbackTest {

    @Test
    public void dummy () {
        // empty - tests configuration of test context.
    }

    @Test
    public void SendMail () {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-context.xml");
        NotificationService notificationService = (EmailServiceImpl) context.getBean("emailService");
        assertTrue(notificationService.sendNotification("ivh11b1@rsgrijks.nl", "ivh11b1@rsgrijks.nl", "Test", "Hallo test test"));
    }
}