package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotAcceptableException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Language;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;
import ro.quickorder.backend.service.UserAttributeService;
import ro.quickorder.backend.service.UserService;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

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
        User user1 = new User("Alex", "parola1", "alex@yahoo.com");
        User user2 = new User("Radu", "parola2", "aadu@yahoo.com");
        User user3 = new User("Ana", "parola3", "ana@yahoo.com");
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
    public void tearDown(){
        userAttributeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testSetPreference() {
        UserAttributeDto attributeDto = new UserAttributeDto();
        attributeDto.setLanguage(Language.RO);
        long userId = userRepository.findAll().get(0).getId();

        UserDto userDto = new UserDto();
        userDto.setEmail("alex@yahoo.com");
        userDto.setUsername("Alex");

        // no attribute
        try{
            userAttributeService.setPreference(userDto,userDto.getUserAttributeDto());
            assertEquals(false,true);
        }catch (BadRequestException ex){
            assertEquals("No attribute!", ex.getMessage());
        }

        userDto.setUserAttributeDto(attributeDto);

        // Everything is good
        userAttributeService.setPreference(userDto,userDto.getUserAttributeDto());
        User user = userRepository.findById(userId).orElse(null);

        assertEquals( Language.RO, user.getAttribute().getLanguage());

        // bad username
        try{
            userDto.setUsername("newUser");
            userAttributeService.setPreference(userDto, userDto.getUserAttributeDto());
            assertEquals(false,true);
        }catch (NotFoundException ex){
            assertEquals("User not found", ex.getMessage());
        }
    }

    @Test
    public void testLogin(){
        UserDto userDto = new UserDto();
        userDto.setUsername("Alex");
        userDto.setPassword("parola1");
        UserDto userDto1 = userService.login(userDto);
        String actual = userDto1.getUsername();
        assertEquals("Alex", actual);

        // User wrong
        try{
            userDto.setUsername("Alexx");
            userDto.setPassword("parola1");
            UserDto userDto2 = userService.login(userDto);
            assertEquals(false,true);
        }catch (NotFoundException ex){
            assertEquals("User or password are incorrect!", ex.getMessage());
        }

        // Password wrong
        try{
            userDto.setUsername("Alex");
            userDto.setPassword("parola1213");
            UserDto userDto2 = userService.login(userDto);
            assertEquals(false,true);
        }catch (NotFoundException ex){
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
        assertEquals(userDtoRez.getPassword(), null);

        // user already used
        try{
            userDto.setUsername("Alex");
            UserDto userDto1 = userService.signUp(userDto);
            assertEquals(false,true);
        }catch (NotAcceptableException ex){
            assertEquals("UserName is already taken!", ex.getMessage());
        }

        // Password wrong
        try{
            userDto.setUsername("Avram");
            userDto.setEmail("ana@yahoo.com");
            UserDto userDto2 = userService.signUp(userDto);
            assertEquals(false,true);
        }catch (NotAcceptableException ex){
            assertEquals("Email is already taken!", ex.getMessage());
        }

    }

}