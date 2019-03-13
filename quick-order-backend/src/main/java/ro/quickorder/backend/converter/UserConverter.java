package ro.quickorder.backend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.UserDto;

/**
 Converts Commands to their corresponding DTO and vice versa.
 *@author R. Lupoaie
 */

@Component
public class UserConverter {

    @Autowired
    private UserAttributeConverter userAttributeConverter;

    public User toUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAttribute(userAttributeConverter.toUserAttribute(userDto.getUserAttributeDto()));
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
        return userDto;
    }
}
