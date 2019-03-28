package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.exception.NotAcceptableException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Language;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;

import javax.inject.Inject;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersResourceTest {

    @Inject
    private UserService userService;
    @Inject
    private UserAttributeService userAttributeService;

    @Inject
    private UserRepository userRepository;
    @Inject
    private UserAttributeRepository userAttributeRepository;

    @Before
    public void setUp() {
        User user1 = new User("Alex1", "parola1", "alex@yahoo.com");
        User user2 = new User("Radu2", "parola2", "aadu@yahoo.com");
        User user3 = new User("Ana12", "parola3", "ana@yahoo.com");
        UserAttribute userAttribute = new UserAttribute();
        User us1 = userRepository.save(user1);
        userAttributeRepository.save(userAttribute);
        userAttribute.setUser(us1);
        userAttributeRepository.save(userAttribute);

        UserAttribute userAttribute2 = new UserAttribute();
        User us2 = userRepository.save(user2);
        userAttributeRepository.save(userAttribute2);
        userAttribute2.setUser(us2);
        userAttributeRepository.save(userAttribute2);

        UserAttribute userAttribute3 = new UserAttribute();
        User us3 = userRepository.save(user3);
        userAttributeRepository.save(userAttribute3);
        userAttribute3.setUser(us3);
        userAttributeRepository.save(userAttribute3);
    }

    @After
    public void tearDown() {
        userAttributeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testSetPreference() {
        UserAttributeDto attributeDto = new UserAttributeDto();
        attributeDto.setLanguage(Language.RO);
        String userId = userRepository.findAll().get(0).getId();

        UserDto userDto = new UserDto();
        userDto.setEmail("alex@yahoo.com");
        userDto.setUsername("Alex1");

        userDto.setUserAttributeDto(attributeDto);

        userAttributeService.setPreference(userDto, userDto.getUserAttributeDto());
        User user = userRepository.findById(userId);
        assertNotNull(user);
        UserAttribute userAttribute = user.getAttribute();
        assertNotNull(userAttribute);
        assertEquals(Language.RO, userAttribute.getLanguage());
    }

    @Test
    public void testSetPreferenceAttributeIsNull(){
        UserDto userDto = new UserDto();
        userDto.setEmail("alex@yahoo.com");
        userDto.setUsername("Alex");

        try {
            userAttributeService.setPreference(userDto, userDto.getUserAttributeDto());
            fail();
        } catch (BadRequestException ex) {
            assertEquals("No attribute!", ex.getMessage());
        }
    }

    @Test
    public void testSetPreferenceBadUser() {
        UserAttributeDto attributeDto = new UserAttributeDto();
        attributeDto.setLanguage(Language.RO);
        String userId = userRepository.findAll().get(0).getId();

        UserDto userDto = new UserDto();
        userDto.setEmail("newUser@yahoo.com");
        userDto.setUsername("newUser");

        userDto.setUserAttributeDto(attributeDto);

        try {
            userAttributeService.setPreference(userDto, userDto.getUserAttributeDto());
            fail();
        } catch (NotFoundException ex) {
            assertEquals("User not found", ex.getMessage());
        }
    }

    @Test
    public void testLogin() {
        UserDto userDto = new UserDto();
        userDto.setUsername("Alex1");
        userDto.setPassword("parola1");

        UserDto userDto1 = userService.login(userDto);
        String actual = userDto1.getUsername();

        assertEquals("Alex1", actual);
    }

    @Test
    public void testLoginWrongUsername() {
        UserDto userDto = new UserDto();
        userDto.setUsername("Alexx");
        userDto.setPassword("parola1");

        try {
            UserDto userDtoRes = userService.login(userDto);
            fail("The username should be wrong");
        } catch (NotFoundException ex) {
            assertEquals("User or password are incorrect!", ex.getMessage());
        }
    }

    @Test
    public void testLogInWrongPassword(){
        UserDto userDto = new UserDto();
        userDto.setUsername("Alex");
        userDto.setPassword("parola1213");

        try {
            UserDto userDtoRes = userService.login(userDto);
            fail("The password should be wrong");
        } catch (NotFoundException ex) {
            assertEquals("User or password are incorrect!", ex.getMessage());
        }
    }

    @Test
    public void testSignUp(){
        UserDto userDto = new UserDto();
        userDto.setUsername("Andrei");
        userDto.setPassword("password");
        userDto.setEmail("Andrei@yahoo.com");

        UserDto userDtoRez = userService.signUp(userDto);
        assertEquals(userDtoRez.getEmail(), userDto.getEmail());
        assertEquals(userDtoRez.getUsername(), userDto.getUsername());
        assertNull(userDtoRez.getPassword());
    }

    @Test
    public void testSingUpUserIsNull(){

        try {
            UserDto userDtoRez = userService.signUp(null);
            fail("User is null, it should throw a BadRequestException");
        } catch(BadRequestException e){
            assertEquals("User is null!", e.getMessage());
        }
    }

    @Test
    public void testSignUpInvalidUsername(){
        try{
            UserDto userDtoTest = new UserDto();
            userDtoTest.setUsername("Dinu)");
            userDtoTest.setPassword("password");
            userDtoTest.setEmail("Dinu@yahoo.com");

            UserDto res = userService.signUp(userDtoTest);
            fail("The username should be invalid, it contains characters that are not allowed!");
        }catch (ForbiddenException e){
            assertEquals("UserName has characters that are not allowed!",e.getMessage());
        }
    }

    @Test
    public void testSignUpUserAlreadyUsed(){
        UserDto userDto = new UserDto();
        userDto.setUsername("Alex1");
        userDto.setPassword("password");
        userDto.setEmail("Andrei@yahoo.com");

        try{
            UserDto userDto1 = userService.signUp(userDto);
            fail("User should be already used!");
        }catch (NotAcceptableException ex){
            assertEquals("UserName is already taken!", ex.getMessage());
        }
    }

    @Test
    public void testSignUpEmailAlreadyUsed(){
        UserDto userDto = new UserDto();
        userDto.setUsername("Andrei");
        userDto.setPassword("password");
        userDto.setEmail("ana@yahoo.com");

        try{
            UserDto userDto2 = userService.signUp(userDto);
            fail("Email should already be used!");
        }catch (NotAcceptableException ex){
            assertEquals("Email is already taken!", ex.getMessage());
        }
    }

}