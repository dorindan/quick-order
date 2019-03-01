package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.convertors.UserAttributeConvertor;
import ro.quickorder.backend.convertors.UserConvertor;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserRepository;
import ro.quickorder.backend.services.UserAttributeService;
import ro.quickorder.backend.services.UserService;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UsersResource {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAttributeService userAttributeService;
    @Autowired
    private UserService userService;


    @RequestMapping(path = "/{id}",method = RequestMethod.GET)
    public String findById(@PathVariable(value = "id") Long id) {
        return userRepository.findById(id).toString();
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDto login(@RequestBody UserDto userDto) {
        return userService.login(userDto);
    }

    @RequestMapping(path = "/signUp", method = RequestMethod.POST)
    public boolean register(@RequestBody String username, @RequestBody String password, @RequestBody String email) {
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping(path = "/{userId}/attributes")
    public void setPreference(@PathVariable("userId") @Positive long userId, @NotNull @RequestBody UserAttributeDto userAttributeDto)
    {
        userAttributeService.setPreference(userId, userAttributeDto);
    }

}