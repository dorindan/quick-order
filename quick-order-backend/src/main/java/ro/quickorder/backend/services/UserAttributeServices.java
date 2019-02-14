package ro.quickorder.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.exception.ForbiddenEx;
import ro.quickorder.backend.exception.NotFoundEx;
import ro.quickorder.backend.model.Language;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;

import javax.inject.Inject;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;


@Service
public class UserAttributeServices {

    @Autowired
    private UserRepository userRepository;
    @Inject
    private UserAttributeRepository userAttributeRepository;

    public void setPreference(long userId, UserAttribute userAttribute) throws RuntimeException{
        User user = userRepository.findById(userId).orElse(null);
        if(userId < 1){
            throw new ForbiddenEx("Invalid user id");
        }
        if(user == null){
            throw new NotFoundEx("User not found");
        }
        userAttribute.setUser(user);
        userAttribute.setId(user.getAttribute().getId());
        user.setAttribute(userAttribute);
        userAttributeRepository.save(user.getAttribute());
        userRepository.save(user);
    }
}
