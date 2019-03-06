package ro.quickorder.backend.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.convertors.UserAttributeConvertor;
import ro.quickorder.backend.convertors.UserConvertor;
import ro.quickorder.backend.exception.*;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;

import javax.inject.Inject;

@Service
public class UserService {

    @Autowired
    private UserConvertor userConvertor;

    @Autowired
    private UserRepository userRepository;

    @Inject
    private UserAttributeRepository userAttributeRepository;

    public UserDto login(UserDto userDto) {
        User user = userConvertor.convertUserDtoToUser(userDto);
        for (User u : userRepository.findAll()) {
            if (user.getPassword().equals(u.getPassword()) && user.getUsername().equals(u.getUsername())) {
                return new UserDto(u.getUsername(),u.getEmail(), new UserAttributeDto(u.getAttribute()));
            }
        }
        throw new NotFoundException("User or password are incorrect!");
    }

    public UserDto signUp(UserDto userDto) {

        // test if username is ok
        for (User u : userRepository.findAll())
            if(u.getUsername().equals(userDto.username)) {
                throw new NotAcceptableException("UserName is already taken!");
            }
        // test if email is ok
        for (User u : userRepository.findAll())
            if(u.getEmail().equals(userDto.email)) {
                throw new NotAcceptableException("Email is already taken!");
            }

        User user = userConvertor.convertUserDtoToUser(userDto);
            System.out.println("------>>>" + user.getUsername() + " " + user.getPassword() + " " + user.getEmail());
        User newUser = userRepository.save(user);
        UserAttribute userAttribute = new UserAttribute();
        userAttributeRepository.save(userAttribute);
        userAttribute.setUser(newUser);
        UserAttribute newAttribute = userAttributeRepository.save(userAttribute);
        newUser.setAttribute(newAttribute);

        UserDto userDtoRez = new UserDto(newUser.getUsername(), newUser.getEmail(),
                new UserAttributeDto(newUser.getAttribute()));
        return userDtoRez;
    }
}
