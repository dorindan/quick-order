package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.service.UserAttributeService;
import ro.quickorder.backend.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @PreAuthorize("hasRole('WAITER')")
    @GetMapping("/users/all")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('WAITER')")
    @PutMapping("/users/update")
    public void updateUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
    }

}