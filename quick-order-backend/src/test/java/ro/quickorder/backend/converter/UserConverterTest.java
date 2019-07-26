package ro.quickorder.backend.converter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ro.quickorder.backend.model.Role;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.model.enumeration.Language;
import ro.quickorder.backend.model.enumeration.RoleName;
import ro.quickorder.backend.repository.RoleRepository;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for {@link UserConverter}
 *
 * @author R. Lupoaie
 */
@ActiveProfiles("junit")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserConverterTest {
    @Autowired
    UserConverter userConverter;
    @Autowired
    RoleRepository roleRepository;

    @Before
    public void setUp() {
        roleRepository.save(new Role(RoleName.ROLE_USER));
    }

    @After
    public void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    public void testConverterUserToDto() {
        User user = new User("name", "password", "email");
        UserAttribute userAttribute = new UserAttribute(Language.RO);
        user.setAttribute(userAttribute);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(RoleName.ROLE_USER));
        user.setRoles(roles);
        UserDto userDto = userConverter.toUserDto(user);
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getRoles().getClass().getName(), userDto.getRoles().getClass().getName());
        assertEquals(user.getAttribute().getLanguage(), userDto.getUserAttributeDto().getLanguage());
    }

    @Test
    public void testConverterUserToDtoWhenUserIsNull() {
        UserDto userDto = userConverter.toUserDto(null);
        assertNull(userDto);
    }

    @Test
    public void testConverterDtoToUser() {
        UserAttributeDto userAttributeDto = new UserAttributeDto(Language.RO);
        UserDto userDto = new UserDto("name", "password", "email", userAttributeDto);
        Set<String> rolesAsStrings = new HashSet<>();
        rolesAsStrings.add("user");
        userDto.setRoles(rolesAsStrings);
        User user = userConverter.toUser(userDto);
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.getUsername(), user.getUsername());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getUserAttributeDto().getLanguage(), user.getAttribute().getLanguage());
        assertNull(user.getFeedbacks());
        assertNull(user.getReservations());
        assertNull(user.getCommands());
    }

    @Test
    public void testConverterDtoToUserWhenDtoIsNull() {
        User user = userConverter.toUser(null);
        assertNull(user);
    }
}