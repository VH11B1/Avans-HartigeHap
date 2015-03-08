package edu.avans.hartigehap.service;

import edu.avans.hartigehap.service.impl.EmailServiceImpl;
import edu.avans.hartigehap.service.testutil.AbstractTransactionRollbackTest;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by Mark on 9-3-2015.
 */
public class EmailServiceTest extends AbstractTransactionRollbackTest {

    @Test
    public void dummy() {
        // empty - tests configuration of test context.
    }

    @Test
    public void SendMail(){
        NotificationService notificationService = new EmailServiceImpl();
        assertTrue(notificationService.sendNotification("mbartele@avans.nl", "Henk"));
    }
}
