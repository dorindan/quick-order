package ro.quickorder.backend.convertors;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.UserDto;

@Component
public class UserConvertor {

    public User convertUserDtoToUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
