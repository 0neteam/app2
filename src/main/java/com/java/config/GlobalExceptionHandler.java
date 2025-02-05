package com.java.config;

import org.springframework.ui.Model;
import org.springframework.security.access.AccessDeniedException;
// import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AccessDeniedException.class)
    public String handle403(AccessDeniedException e, Model model) {
        model.addAttribute("error", "You do not have permission to access this resource.");
        model.addAttribute("msg", "해당 페이지에 접근 권한이 없습니다.");
        return "errorPage";
    }
	
//	@ExceptionHandler(NoHandlerFoundException.class)
//    public String handle404(NoHandlerFoundException e, Model model) {
//        model.addAttribute("error", "Page not found!");
//        model.addAttribute("msg", "해당 페이지는 지원하지 않습니다.");
//        return "errorPage";
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String handle405(HttpRequestMethodNotSupportedException e, Model model) {
        model.addAttribute("error", "Method not allowed!");
        model.addAttribute("msg", "요청하신 Method가 없습니다.");
        return "errorPage";
    }
    
}
