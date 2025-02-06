package com.java.prod;

import org.springframework.web.bind.annotation.GetMapping;

public class prodController {

	@GetMapping("/inventory")
	public String prod() {
		return "prod/inventory";
	}
	
}
