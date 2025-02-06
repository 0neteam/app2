package com.java.transp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class TranspController {

    @GetMapping("/transpEmail")
    public String TranspEmail() {
        return "transp/transpEmail";
    };
    
    @GetMapping("/transp")
    public String Transp() {
        return "transp/transp";
    };

}
