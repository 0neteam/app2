package com.java.prod;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor

public class prodController {
	
	@GetMapping("/inventory")
	public String prod() {
	
		return "prod/inventory";
	}
	
}
