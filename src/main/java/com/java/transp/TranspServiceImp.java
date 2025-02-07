package com.java.transp;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TranspServiceImp implements TranspService {

	private final TranspDao transpDao;

	public TranspServiceImp(TranspDao transpDao) {
		this.transpDao = transpDao;
	}

	@Override
	public String list(Model model, HttpServletRequest req) {
		List<TranspDTO> transplist = transpDao.transList();
		model.addAttribute("transplist", transplist);
		System.out.println("1111111111");
		System.out.println("transplist: " + transplist);
		return "transp/transp";
	}
	
	@Override
	public String InfoSave(HttpServletRequest req) {
		int bizNo = Integer.parseInt(req.getParameter("bizNo"));
		String driverName = req.getParameter("driverName");
		String driverPhone = req.getParameter("driverPhone");
		TranspInfoDTO transpInfoDTO = TranspInfoDTO.builder().bizNo(bizNo).driverName(driverName).driverPhone(driverPhone).build();
		transpInfoDTO = transpDao.InfoSave(transpInfoDTO);
		if(transpInfoDTO == null) {
			return "redirect:/transpEmail";				
		} else {
			return "redirect:/";
		}
	}
	
}
