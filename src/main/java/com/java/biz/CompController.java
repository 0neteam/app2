package com.java.biz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class CompController {

    private final BizService bizService;

//    @GetMapping("/comp/create")
//    public String create(HttpServletRequest req){
//        return bizService.create(req);
//
//    }
}
