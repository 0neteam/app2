package com.java.transp;

import java.util.List;

import org.springframework.ui.Model;

import com.java.biz.BizDTO;
import com.java.quo.QuoModalDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface TranspService {

    public String transpList(Model model, HttpServletRequest req);
    public String transpSearch(Model model, HttpServletRequest req);
    public List<TranspModalDTO> transpModals(String transpNo);
	public TranspResDTO InfoSave(TranspInfoDTO transpInfoDTO);
	public TranspInfoDTO getTranspInfo(int driverNo);
	public List<BizDTO> getAllBizNames();
	public String processEmailSending(Integer quoNo, Integer bizNo, Model model);
    public Boolean sendEmailToClient(int bizNo, int quoNo, int transpNo);
    public List<TranspQuoDTO> getMfrQuoByBizNo(int quoNo);

    // 운송 취소 처리 메서드 추가
    public Boolean cancelTransp(int transpNo);  // 운송 취소 처리 및 이메일 발송

    public TranspDTO findTransInfo(int transpNo);
    public TranspResDTO successTransp(int transpNo);
    
}
