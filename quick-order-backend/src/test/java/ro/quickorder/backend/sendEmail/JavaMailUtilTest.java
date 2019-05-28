package ro.quickorder.backend.sendEmail;

import org.junit.Test;

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

            JavaMailUtil.sendMail("robert_eu_97@yahoo.com");
            assertTrue(true);

    }
}
