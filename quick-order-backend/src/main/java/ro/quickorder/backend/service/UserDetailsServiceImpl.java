package ro.quickorder.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ro.quickorder.backend.repository.UserRepository;
import ro.quickorder.backend.security.UserDetailsDto;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userdto = userRepository.findByUsername(username).map((user) -> {
			return new UserDetailsDto(user);
		}).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		return userdto;
	}

}
