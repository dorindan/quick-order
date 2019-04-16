package ro.quickorder.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.UserAttributeConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.NotFoundException;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;


@Service
public class UserAttributeService {
    private static final Logger LOG = LoggerFactory.getLogger(UserAttributeService.class);
    @Autowired
    private UserAttributeConverter userAttributeConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAttributeRepository userAttributeRepository;

    public void setPreference(UserDto userDto, UserAttributeDto userAttributeDto) {
        if (userAttributeDto == null) {
            LOG.error("No attribute!");
            throw new BadRequestException("No attribute!");
        }
        UserAttribute userAttribute = userAttributeConverter.toUserAttribute(userAttributeDto);
        // identify user using userName
        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null) {
            LOG.error("User not found");
            throw new NotFoundException("User not found");
        }
        userAttribute.setUser(user);
        userAttribute.setId(user.getAttribute().getId());
        user.setAttribute(userAttribute);
        userAttributeRepository.save(user.getAttribute());
        userRepository.save(user);
    }
}
