package com.java.transp;

import java.util.List;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

public interface TranspService {

	public String list(Model model, HttpServletRequest req);
	public String InfoSave(HttpServletRequest req);
	public TranspInfoDTO getTranspInfo(int driverNo);
    public Boolean sendEmailToClient(int bizNo);
	public String bizNoEmail(int bizNo);
	public List<String> getAllBizNames();
	public String processEmailSending(int bizNo, String carrier);
	public int getBizNoByBizName(String bizName);
    public TranspQuoDTO getMfrQuoByBizNo(int bizNo);  // bizNo로 MfrQuo 조회
	
}
