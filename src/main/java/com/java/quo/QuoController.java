package com.java.quo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuoController {
	
	@GetMapping("/quo")
	public String quo() {
		return "quo";
	};

}
