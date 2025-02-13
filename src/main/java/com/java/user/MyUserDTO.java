package com.java.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDTO implements UserDetails {

	private UserDTO userDTO;
	private List<RoleDTO> roleDTO;

	public MyUserDTO(UserDTO user, List<RoleDTO> role) {
		this.userDTO = user;
		this.roleDTO = role;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> grant = new HashSet<>();
		// roleDTO 리스트를 돌면서 각 RoleDTO의 이름을 사용하여 SimpleGrantedAuthority 추가
	    for (RoleDTO role : roleDTO) {
	        grant.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName())));
	    }
	    
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
