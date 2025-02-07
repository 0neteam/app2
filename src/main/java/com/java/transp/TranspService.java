package com.java.transp;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

public interface TranspService {

	public String list(Model model, HttpServletRequest req);
	public String InfoSave(HttpServletRequest req);
	
}
