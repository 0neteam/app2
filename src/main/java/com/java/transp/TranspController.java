package com.java.transp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class TranspController {
	
	private final TranspService transpService;
	
    public TranspController(TranspService transpService) {
        this.transpService = transpService;
    }

    @GetMapping("/transpEmail/{bizNo:[0-9]+}")
    public String TranspEmail(@PathVariable(name = "bizNo") Integer bizNo, Model model) {
    	model.addAttribute("bizNo", bizNo);
        return "transp/transpEmail";
    };
    
    @GetMapping("/transp")
    public String list(Model model, HttpServletRequest req) {
    	System.out.println("22222222222");
        return transpService.list(model, req);
    };
    
    @PostMapping("/InfoSave")
    public String InfoSave(HttpServletRequest req) {
    	return transpService.InfoSave(req);
    }

}
