package ro.quickorder.backend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.Role;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.UserDto;

import java.util.Set;

/**
 * Converts Users to their corresponding DTO and vice versa.
 *
 * @author R. Lupoaie
 */

@Component
public class UserConverter {

    @Autowired
    private UserAttributeConverter userAttributeConverter;
    @Autowired
    private RoleConverter roleConverter;

    public UserConverter() {

    }

    public UserConverter(UserAttributeConverter userAttributeConverter, RoleConverter roleConverter) {
        this.userAttributeConverter = userAttributeConverter;
        this.roleConverter = roleConverter;
    }

    public User toUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAttribute(userAttributeConverter.toUserAttribute(userDto.getUserAttributeDto()));
        Set<String> rolesAsStrings = userDto.getRoles();
        Set<Role> roles = roleConverter.toRoleSet(rolesAsStrings);
        user.setRoles(roles);
        return user;
    }

    public UserDto toUserDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setUserAttributeDto(userAttributeConverter.toUserAttributeDto(user.getAttribute()));
        Set<Role> roles = user.getRoles();
        Set<String> rolesAsString = roleConverter.toStringSet(roles);
        userDto.setRoles(rolesAsString);
        return userDto;
    }
}
