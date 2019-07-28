package ro.quickorder.backend.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.Role;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.model.enumeration.Language;
import ro.quickorder.backend.model.enumeration.RoleName;
import ro.quickorder.backend.repository.RoleRepository;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;
import ro.quickorder.backend.resource.response.ResponseMessage;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAttributeService userAttributeService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAttributeRepository userAttributeRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        User user1 = new User("hellohello", "$2a$10$bO..vvSzK55NYvsGUdF1s.W9uBCGM8rIHDB/sSGRl2UARiKXrR/7C", "hello@yahoo.com");
        UserAttribute userAttribute = new UserAttribute();
        User us1 = userRepository.save(user1);
        userAttributeRepository.save(userAttribute);
        userAttribute.setUser(us1);
        userAttributeRepository.save(userAttribute);
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
        userDto.setEmail("hello@yahoo.com");
        userDto.setUsername("hellohello");
        userDto.setUserAttributeDto(attributeDto);
        userAttributeService.setPreference(userDto, userDto.getUserAttributeDto());
        User user = userRepository.findById(userId);
        assertNotNull(user);
        UserAttribute userAttribute = user.getAttribute();
        assertNotNull(userAttribute);
        assertEquals(Language.RO, userAttribute.getLanguage());
    }

    @Test(expected = BadRequestException.class)
    public void testSetPreferenceAttributeIsNull() {
        UserDto userDto = new UserDto();
        userDto.setEmail("alex@yahoo.com");
        userDto.setUsername("Alex");

        userAttributeService.setPreference(userDto, userDto.getUserAttributeDto());
    }

    @Test(expected = NotFoundException.class)
    public void testSetPreferenceBadUser() {
        UserAttributeDto attributeDto = new UserAttributeDto();
        attributeDto.setLanguage(Language.RO);
        UserDto userDto = new UserDto();
        userDto.setEmail("newUser@yahoo.com");
        userDto.setUsername("newUser");
        userDto.setUserAttributeDto(attributeDto);

        userAttributeService.setPreference(userDto, userDto.getUserAttributeDto());
    }

    @Test
    public void testLogin() {
        UserDto userDto = new UserDto();
        userDto.setUsername("hellohello");
        userDto.setPassword("hellohello");
        ResponseEntity<?> responseEntity = userService.login(userDto);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test(expected = InternalAuthenticationServiceException.class)
    public void testLoginWrongUsername() {
        UserDto userDto = new UserDto();
        userDto.setUsername("wromg_username");
        userDto.setPassword("hellohello");
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        userDto.setRole(roles);

        userService.login(userDto);
    }

    @Test(expected = BadCredentialsException.class)
    public void testLogInWrongPassword() {
        UserDto userDto = new UserDto();
        userDto.setUsername("hellohello");
        userDto.setPassword("parola1213");
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        userDto.setRole(roles);

        userService.login(userDto);
    }

    @Test
    public void testSignUp() {
        UserDto userDto = new UserDto();
        userDto.setUsername("test_user");
        userDto.setPassword("password");
        userDto.setEmail("test@yahoo.com");
        Role role = new Role(RoleName.ROLE_USER);
        roleRepository.save(role);
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        userDto.setRole(roles);
        ResponseEntity<?> responseEntity = userService.signUp(userDto);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test(expected = BadRequestException.class)
    public void testSingUpUserIsNull() {
        userService.signUp(null);
    }

    @Test(expected = ForbiddenException.class)
    public void testSignUpInvalidUsername() {
        UserDto userDtoTest = new UserDto();
        userDtoTest.setUsername("hello)");
        userDtoTest.setPassword("password");
        userDtoTest.setEmail("hello@yahoo.com");

        userService.signUp(userDtoTest);
    }

    @Test
    public void testSignUpUserAlreadyUsed() {

        UserDto userDto = new UserDto();
        userDto.setUsername("hellohello");
        userDto.setPassword("hellohello");
        userDto.setEmail("helloo@yahoo.com");
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        userDto.setRole(roles);
        ResponseEntity<?> responseEntity = userService.signUp(userDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        ResponseMessage rm = (ResponseMessage) responseEntity.getBody();
        assertEquals(new ResponseMessage("Fail -> Username is already taken!").getMessage(), rm.getMessage());
    }

    @Test
    public void testSignUpEmailAlreadyUsed() {
        UserDto userDto = new UserDto();
        userDto.setUsername("hellohelloo");
        userDto.setPassword("hellohello");
        userDto.setEmail("hello@yahoo.com");
        Set<String> roles = new HashSet<String>();
        roles.add("user");
        userDto.setRole(roles);
        ResponseEntity<?> responseEntity = userService.signUp(userDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        ResponseMessage rm = (ResponseMessage) responseEntity.getBody();
        assertEquals(new ResponseMessage("Fail -> Email is already in use!").getMessage(), rm.getMessage());
    }

}