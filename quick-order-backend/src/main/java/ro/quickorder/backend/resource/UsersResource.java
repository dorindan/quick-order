package ro.quickorder.backend.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import ro.quickorder.backend.model.Users;
import ro.quickorder.backend.repository.ReservationRepository;
import ro.quickorder.backend.repository.UsersRepository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import java.security.Principal;
import java.util.Base64;
import java.util.List;


@RestController
@CrossOrigin
public class UsersResource {
    @Autowired
    UsersRepository userRepository;

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String findById(@RequestParam(value = "id", defaultValue = "0") Long id) {
        return userRepository.findById(id).toString();
    }

    @RequestMapping(path = "/users/all", method = RequestMethod.GET)
    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public boolean login(@RequestBody Users user) {
        for (Users u : getUsers())
        {
            if (user.getPassword().equals(u.getPassword()) && user.getUsername().equals(u.getUsername()))
            {
                return true;
            }
        }
        return false;
    }


    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @RequestMapping(path = "/signUp", method = RequestMethod.POST)
    public boolean register(@RequestBody String username, @RequestBody String password, @RequestBody String email) {
        try{
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}