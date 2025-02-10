package com.java.quo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuoController {
	
	private final QuoService quoService;
	
	@GetMapping("/quo")
	public String list(Model model, HttpServletRequest req) {
		return quoService.list(model, req);
	};
}
