package com.dkoroliuk.hotel_spring.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom implementation of {@link UserDetails} interface. Used for
 * authentication
 */
public class AppUserDetails implements UserDetails {

	private static final long serialVersionUID = 4025330751239008786L;
	private final long id;
	private final String login;
	private final String password;
	private final boolean isEnable;
	private final List<GrantedAuthority> authorities;

	public AppUserDetails(User user) {
		id = user.getId();
		login = user.getLogin();
		password = user.getPassword();
		isEnable = user.isEnable();
		authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().getRole().toUpperCase()));
	}

	public long getId() {
		return id;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return login;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return isEnable;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AppUserDetails that = (AppUserDetails) o;

		return login.equals(that.login);
	}

	@Override
	public int hashCode() {
		return login.hashCode();
	}
}