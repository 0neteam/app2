package com.java.transp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


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
        return transpService.list(model, req);
    };
    
    @GetMapping("/transpInfo/{transpMailNo}")
    public String getTranspInfo(@PathVariable("transpMailNo") Integer transpMailNo, Model model, HttpServletRequest req) {
    	transpService.list(model, req);
        TranspInfoDTO driverInfo = transpService.getTranspInfo(transpMailNo);
        model.addAttribute("driverInfo", driverInfo);
        return "transp/transp";
    }
    
    @PostMapping("/InfoSave")
    public String InfoSave(HttpServletRequest req) {
    	return transpService.InfoSave(req);
    }

    @GetMapping("/transpTest")
    public String showTranspTestPage(Model model) {
        return "transp/transpTest";  // 반환되는 뷰 이름을 설정
    }
    
    @PostMapping("/sendEmail")
    public String sendEmail(Model model, HttpSession session) {
        String to = "receiver@example.com";  // 실제 이메일 주소로 변경
        String subject = "테스트 이메일";
        String body = "이메일이 정상적으로 전송되었습니다.";

        // 이메일 전송
        transpService.sendEmail(to, subject, body);

        // 세션에 메시지 저장
        session.setAttribute("message", "이메일 전송 성공!");

        return "transp/transp";  // 이메일 전송 후 같은 페이지로 포워딩
    }
    
}
