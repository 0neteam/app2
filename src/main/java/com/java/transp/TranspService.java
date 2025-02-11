package com.java.transp;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

public interface TranspService {

	public String list(Model model, HttpServletRequest req);
	public String InfoSave(HttpServletRequest req);
	public TranspInfoDTO getTranspInfo(int driverNo);
	public void sendEmailsToActiveClients(String subject, String body);
	public void sendEmail(String to, String subject, String body);
}
