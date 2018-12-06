package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Base64;
import java.util.List;


@RestController
@CrossOrigin
public class UsersResource {
    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return userRepository.findById(id).toString();
    }

    @RequestMapping(path = "/users/all", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping("/login")
    public boolean login(@RequestBody User user) {
        try {
            User userFromDb = userRepository.findFirstByUsername(user.getUsername());
            return userFromDb.getPassword().equals(user.getPassword());
        }
        catch(Exception e)
        {
            return false;
        }
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization")
                .substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder()
                .decode(authToken)).split(":")[0];
    }
}
