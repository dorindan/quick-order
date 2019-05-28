package ro.quickorder.backend.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.quickorder.backend.converter.UserAttributeConverter;
import ro.quickorder.backend.converter.UserConverter;
import ro.quickorder.backend.exception.BadRequestException;
import ro.quickorder.backend.exception.ForbiddenException;
import ro.quickorder.backend.model.Role;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserDto;
import ro.quickorder.backend.model.enumeration.RoleName;
import ro.quickorder.backend.repository.RoleRepository;
import ro.quickorder.backend.repository.UserAttributeRepository;
import ro.quickorder.backend.repository.UserRepository;
import ro.quickorder.backend.resource.response.JwtResponse;
import ro.quickorder.backend.resource.response.ResponseMessage;
import ro.quickorder.backend.security.jwt.JwtProvider;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserAttributeConverter userAttributeConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAttributeRepository userAttributeRepository;

    public ResponseEntity<?> login(UserDto userDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }

    public ResponseEntity<?> signUp(UserDto userDto) {
        if (userDto == null) {
            LOG.error("User is null!");
            throw new BadRequestException("User is null!");
        }
        String line = userDto.getUsername();
        String pattern = "^[a-zA-Z0-9_.]{5,}$";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);
        // Now create matcher object.
        Matcher m = r.matcher(line);
        // bad username
        if (!m.find()) {
            LOG.error("UserName has characters that are not allowed!");
            throw new ForbiddenException("UserName has characters that are not allowed!");
        }
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
        // Creating user's account
        User user = new User(userDto.getUsername(),
                encoder.encode(userDto.getPassword()), userDto.getEmail());
        Set<String> rolesAsString = userDto.getRoles();
        if (userDto.getRoles().isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Fail -> No Roles set!"),
                    HttpStatus.BAD_REQUEST);
        }
        Set<Role> roles = new HashSet<>();
        rolesAsString.stream().map(RoleName::from)
                .map(role -> roleRepository.findByName(role)
                        .orElseThrow(() -> new RuntimeException("Could not find Role " + role.name())))
                .forEach(roles::add);

        userRepository.save(user);
        UserAttribute userAttribute = new UserAttribute();
        userAttributeRepository.save(userAttribute);
        userAttribute.setUser(user);
        UserAttribute newAttribute = userAttributeRepository.save(userAttribute);
        user.setAttribute(newAttribute);
        user.setRoles(roles);
        userRepository.save(user);
        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }
}
