package ro.quickorder.backend.security;

import org.springframework.security.core.userdetails.UserDetails;

import ro.quickorder.backend.model.User;

public class UserDetailsDto extends User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserDetailsDto() {

	}

	public UserDetailsDto(User user) {
		this.setId(user.getId());
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setAuthorities(user.getAuthorities());
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
