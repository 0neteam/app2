package com.java.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TestController {

    private final TestMapper testMapper;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }
    
    @ResponseBody
    @GetMapping("/test")
    public int test(@RequestParam(name = "no", required = false) Integer no) {
    	return testMapper.test(no == null ? 0 : no );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String about(Model model) {
        model.addAttribute("title", "Admin");
        return "admin";
    }
    
    @GetMapping("/signIn")
	public String signIn() {return "signIn";}
	
	@GetMapping("/signUp")
	public String signUp() {return "signUp";}
	
	@PostMapping("/test")
	public String test() {return "redirect:/";}

}
