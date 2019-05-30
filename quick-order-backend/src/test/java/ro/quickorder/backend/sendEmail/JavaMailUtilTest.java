package ro.quickorder.backend.sendEmail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * @author R. Lupoaie
 */
public class JavaMailUtilTest {

    @Test
    public void sendMailTest(){
            JavaMailUtil.sendMail("robert_tu_1@yahoo.com","Test", "This is a test email.");
            assertTrue(true);
    }
}
