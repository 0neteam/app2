package com.java.quo;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;

public interface QuoService {
	
	public String list(Model model, HttpServletRequest req);

}
