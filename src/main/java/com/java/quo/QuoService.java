package com.java.quo;

import org.springframework.ui.Model;

public interface QuoService {
	
	public String list(Model model, QuoSearchDTO quoSearchDTO);
	public QuoModalDTO quoModal(String quoNo);

}
