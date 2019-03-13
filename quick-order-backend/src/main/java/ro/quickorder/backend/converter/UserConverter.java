package ro.quickorder.backend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.UserDto;

@Component
public class UserConverter {

    @Autowired
    private UserAttributeConverter userAttributeConverter;

    public User convertUserDtoToUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAttribute(userAttributeConverter.convertUserAttrDtoToUserAttribute(userDto.getUserAttributeDto()));
        return user;
    }

    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setEmail(user.getEmail());
        userDto.setUserAttributeDto(userAttributeConverter.convertUserAttributeToUserAttrDto(user.getAttribute()));
        return userDto;
    }
}
