package ro.quickorder.backend.convertors;

import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;

public class UserConvertor {

    public User convertUserDtoToUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.username);
        user.setPassword(userDto.password);
        return user;
    }
}
