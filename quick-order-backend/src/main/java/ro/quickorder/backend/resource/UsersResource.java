package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.service.UserAttributeService;
import ro.quickorder.backend.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UsersResource {
    @Autowired
    private UserAttributeService userAttributeService;
    @Autowired
    private UserService userService;

    @PostMapping(path = "/users/attributes")
    public void setPreference(@NotNull @RequestBody UserDto userDto) {
        userAttributeService.setPreference(userDto, userDto.getUserAttributeDto());
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserDto userDto) {
        return userService.login(userDto);
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDto userDto) {
        return userService.signUp(userDto);
    }

}