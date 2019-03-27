package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserRepository;
import ro.quickorder.backend.service.UserAttributeService;
import ro.quickorder.backend.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UsersResource {
    @Autowired
    private UserAttributeService userAttributeService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDto login(@NotNull @RequestBody UserDto userDto) {
        return userService.login(userDto);
    }

    @RequestMapping(path = "/signUp", method = RequestMethod.POST)
    public UserDto signUp(@NotNull @RequestBody UserDto userDto) {
        return userService.signUp(userDto);
    }

    @PostMapping(path = "/attributes")
    public void setPreference(@NotNull @RequestBody UserDto userDto)
    {
        userAttributeService.setPreference(userDto, userDto.getUserAttributeDto());
    }

}