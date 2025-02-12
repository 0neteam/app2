package com.java.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDTO implements UserDetails {

	private UserDTO userDTO;
	private RoleDTO roleDTO;

	public MyUserDTO(UserDTO user, RoleDTO role) {
		this.userDTO = user;
		this.roleDTO = role;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> grant = new HashSet<>();
		grant.add( new SimpleGrantedAuthority("ROLE_".concat(roleDTO.getName())) );
		return grant;
	}

	@Override
	public String getPassword() {
		return userDTO.getPwd();
	}

	@Override
	public String getUsername() {
		return userDTO.getEmail();
	}
	
	public int getNo() {
		return userDTO.getUserNo();
	}

}
