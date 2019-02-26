package ro.quickorder.backend.resource;

import org.h2.tools.Server;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.exception.ForbiddenEx;
import ro.quickorder.backend.exception.NotFoundEx;
import ro.quickorder.backend.model.Language;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;
import ro.quickorder.backend.model.User;
import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;

import java.sql.SQLException;

import static org.junit.Assert.*;

@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersResourceTest {

    @Inject
    private UsersResource usersResource;
    @Inject
    private UserRepository userRepository;
    @Inject
    private UserAttributeRepository userAttributeRepository;

    @Before
    public void setUp() throws SQLException {
        User user1 = new User("Alex", "parola1", "alex@yahoo.com");
        User user2 = new User("Radu", "parola2", "aadu@yahoo.com");
        User user3 = new User("Ana", "parola3", "ana@yahoo.com");

        UserAttribute userAttribute1 = userAttributeRepository.save(user1.getAttribute());
        User us1 = userRepository.save(user1);
        us1.setAttribute(userAttribute1);
        userAttribute1.setUser(us1);

        UserAttribute userAttribute2 = userAttributeRepository.save(user1.getAttribute());
        User us2 = userRepository.save(user1);
        us2.setAttribute(userAttribute2);
        userAttribute2.setUser(us2);

        UserAttribute userAttribute3 = userAttributeRepository.save(user1.getAttribute());
        User us3 = userRepository.save(user1);
        us3.setAttribute(userAttribute3);
        userAttribute3.setUser(us3);
    }

    @Test
    public void testSetPreference() {
        UserAttributeDto attribute = new UserAttributeDto();
        attribute.language = Language.ro;
        long userId = usersResource.getUsers().get(0).getId();

        //Everything is good
        usersResource.setPreference(userId, attribute);
        User user = userRepository.findById(userId).orElse(null);
        assertEquals( Language.ro, user.getAttribute().getLanguage());

        //Id 0
        try{
            usersResource.setPreference(0, attribute);
            assertEquals(false,true);
        }catch (ForbiddenEx ex){
            assertEquals("Invalid user id", ex.getMessage());
        }

        //Id >0 User not found
        try{
            usersResource.setPreference(10, attribute);
            assertEquals(false,true);
        }catch (NotFoundEx ex){
            assertEquals("User not found", ex.getMessage());
        }

        //bad language enum, how to test?
    }


    @Test
    public void testLogin(){
        UserDto userDto = new UserDto();
        userDto.username = "Alex";
        userDto.password = "parola1";
        UserDto userDto1 = usersResource.login(userDto);
        long actual = userDto1.id;
        assertEquals(1, actual);

        // User wrong
        try{
            userDto.username = "Alexx";
            userDto.password = "parola1";
            UserDto userDto2 = usersResource.login(userDto);
            assertEquals(false,true);
        }catch (NotFoundEx ex){
            assertEquals("User or password are incorrect!", ex.getMessage());
        }

        // Password wrong
        try{
            userDto.username = "Alex";
            userDto.password = "parola1213";
            UserDto userDto2 = usersResource.login(userDto);
            assertEquals(false,true);
        }catch (NotFoundEx ex){
            assertEquals("User or password are incorrect!", ex.getMessage());
        }
    }
}