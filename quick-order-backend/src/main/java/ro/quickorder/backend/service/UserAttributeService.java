package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.convertors.UserAttributeConvertor;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;

import javax.inject.Inject;


@Service
public class UserAttributeService {

    @Autowired
    private UserAttributeConvertor userAttributeConvertor ;

    @Autowired
    private UserRepository userRepository;
    @Inject
    private UserAttributeRepository userAttributeRepository;

    public void setPreference(UserDto userDto, UserAttributeDto userAttributeDto) {

        if(userAttributeDto == null){
            throw new BadRequestException("No attribute!");
        }

        UserAttribute userAttribute = userAttributeConvertor.convertUserAttrDtoToUserAttribute(userAttributeDto);

        // identify user using userName
        User user=userRepository.findByUsername(userDto.getUsername());
        if(user == null){
            throw new NotFoundException("User not found");
        }
        userAttribute.setUser(user);
        userAttribute.setId(user.getAttribute().getId());
        user.setAttribute(userAttribute);
        userAttributeRepository.save(user.getAttribute());
        userRepository.save(user);
    }
}
