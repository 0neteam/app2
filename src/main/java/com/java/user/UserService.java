package com.java.user;

import java.util.List;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

	public String list(Model model, String searchOption, String searchKeyword);
	
	public String save(UserDTO user);
	
	public UserDTO email_du_chk(String email);
	
	public boolean delete(String userNo);
}
