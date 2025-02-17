package com.java.quo;

import java.util.List;

import org.springframework.ui.Model;

public interface QuoService {
	
	public String list(Model model, QuoSearchDTO quoSearchDTO);
	public List<QuoModalDTO> quoModals(String orderNo);
	public String quoChk(String key, int orderNo, Model model);
	public QuoResOrderDTO setQuo(String key, Integer status, QuoReqOrderDTO quoReqOrderDTO);
	public String del(int quoNo);

}
