package com.java.transp;

import java.util.List;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

public interface TranspService {

    public String transpList(Model model, HttpServletRequest req);
    public String transpSearch(Model model, HttpServletRequest req);
    public List<TranspModalDTO> transpModals(String transpNo);
	public String InfoSave(HttpServletRequest req);
	public TranspInfoDTO getTranspInfo(int driverNo);
	public List<String> getAllBizNames();
	public String processEmailSending(int bizNo, String carrier);
    public Boolean sendEmailToClient(int bizNo);
}
