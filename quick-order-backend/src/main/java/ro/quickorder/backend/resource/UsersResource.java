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
import ro.quickorder.backend.services.UserAttributeServices;
import ro.quickorder.backend.services.UserServices;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.ws.rs.ClientErrorException;
import java.security.Principal;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/api")
public class UsersResource {
    private final UserAttributeConvertor userAttributeConvertor = new UserAttributeConvertor();
    private final UserConvertor userConvertor = new UserConvertor();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAttributeServices userAttributeServices;
    @Autowired
    private UserServices userServices;


    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return userRepository.findById(id).toString();
    }

    @RequestMapping(path = "/users/all", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(path = "save", method = RequestMethod.POST)
    public void save(@RequestBody User user) {
        userRepository.save(user);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDto login(@RequestBody UserDto userDto) throws ClientErrorException{
        User user = userConvertor.convertUserDtoToUser(userDto);
        return userServices.login(user);
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(path = "/signUp", method = RequestMethod.POST)
    public boolean register(@RequestBody String username, @RequestBody String password, @RequestBody String email) {
        try {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @PostMapping(path = "/users/{userId}/attributes")
    public void setPreference(@PathVariable("userId") @Positive long userId, @NotNull @RequestBody UserAttributeDto userAttribute)
    throws ClientErrorException{
        UserAttribute newUserAttribute = userAttributeConvertor.convertUserAttrDtoToUserAttribute(userAttribute);
        userAttributeServices.setPreference(userId, newUserAttribute);
        System.out.println("Am reusit");
    }

}