package com.java.biz;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.ui.Model;

public interface BizService {

    String list(Model model, Map<String, String> paramMap);

	String detail(Model model, HttpServletRequest req);

    BizResDTO update(Map<String, String> paramMap);

    BizResDTO delete(int bizNo);

    BizResDTO create(Map<String, String> paramMap);

}
