package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.Language;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Unit test for {@link UserConverter}
 *
 * @author R. Lupoaie
 */
public class UserConverterTest {
    private UserConverter userConverter = new UserConverter(new UserAttributeConverter());

    @Test
    public void testConverterUserToDto() {
        User user = new User("name", "password", "email");
        UserAttribute userAttribute = new UserAttribute(Language.RO);
        user.setAttribute(userAttribute);
        UserDto userDto = userConverter.toUserDto(user);
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(user.getUsername(), userDto.getUsername());
        assertEquals(user.getEmail(), userDto.getEmail());
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