package com.java.biz;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@RequestMapping("/biz")
public class BizController {

    private final BizService bizService;

    /*
    * list
    * create
    * detail
    * */
    //

    //조회 //첫화면, 거래처목록 뿌려주는놈.
    @GetMapping("/list")
    public String list(Model model, @RequestParam(required = false) Map<String, String> paramMap) {
        return bizService.list(model, paramMap);
    }

    //상세
    @GetMapping("/detail")
    public String detail(Model model, HttpServletRequest req){
        return bizService.detail(model,req);
    }

    //등록 aka "(창고->제조사) 거래처 가입화면" 거래처 추가
    @GetMapping("/create")
    public String create() {
        return "biz/create";
    }

    @GetMapping("/findPwd")
    public String findPwd() {
        return "biz/findPwd";
    }

    @ResponseBody
    @PostMapping("/checkEmail")
    public BizResDTO checkemail(@RequestParam("email") String email) {
        return bizService.checkemail(email);
    }

    @ResponseBody
    @PostMapping("/loginUpdateAuthCode")
    public BizResDTO loginUpdateAuthCode(@RequestParam("email") String email) {
        return bizService.loginUpdateAuthCode(email);
    }

    @ResponseBody
    @PostMapping("/loginUpdateAuthCodeCheck")
    public BizResDTO loginUpdateAuthCodeCheck(@RequestParam("authCode") String authCode) {
        return bizService.loginUpdateAuthCodeCheck(authCode);
    }

    @ResponseBody
    @PostMapping("/loginpwdupdate")
    public BizResDTO loginpwdupdate(
            @RequestParam("email") String email, 
            @RequestParam("pwd") String pwd) {
        return bizService.loginpwdupdate(email, pwd);
    }


    @ResponseBody
    @PostMapping("/create")
    public BizResDTO create(@RequestParam Map<String, String> paramMap) {
        return bizService.create(paramMap);
    }

    @ResponseBody
    @PostMapping("/update")
    public BizResDTO update(@RequestParam Map<String, String> paramMap) {
        return bizService.update(paramMap);
    }

    @ResponseBody
    @PostMapping("/delete")
    public BizResDTO delete(@RequestParam("bizNo") Integer bizNo) {
        return bizService.delete(bizNo);
    }

}
