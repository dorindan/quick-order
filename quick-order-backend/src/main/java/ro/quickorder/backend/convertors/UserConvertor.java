package ro.quickorder.backend.convertors;

import org.springframework.stereotype.Component;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.UserDto;

@Component
public class UserConvertor {

    public User convertUserDtoToUser(UserDto userDto){
        User user = new User();
        user.setUsername(userDto.username);
        user.setPassword(userDto.password);
        user.setEmail(userDto.email);
        return user;
    }
}
