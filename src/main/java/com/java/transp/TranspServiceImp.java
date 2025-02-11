package com.java.transp;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TranspServiceImp implements TranspService {

	private final TranspDao transpDao;
	private final JavaMailSender mailSender;

	public TranspServiceImp(TranspDao transpDao, JavaMailSender mailSender) {
		this.transpDao = transpDao;
		this.mailSender = mailSender;
	}

	@Override
	public String list(Model model, HttpServletRequest req) {
		List<TranspDTO> transplist = transpDao.transList();
		model.addAttribute("transplist", transplist);
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

	@Override
	public TranspInfoDTO getTranspInfo(int transpMailNo) {
	    return transpDao.findDriverInfo(transpMailNo);
	}
	
    // 활성화된 거래처 이메일을 모두 받아서 이메일 보내기
	@Override
	public void sendEmailsToActiveClients(String subject, String body) {
	    List<String> emailList = transpDao.transpmail(); // 이메일 목록 조회
	    for (String email : emailList) {
	        sendEmail(email, subject, body);  // 각 이메일로 전송
	    }
	}

    // 실제 이메일 보내는 메서드
	@Override
	public void sendEmail(String to, String subject, String body) {
	    try {
	        var message = mailSender.createMimeMessage();
	        var helper = new MimeMessageHelper(message, true, "UTF-8");

	        helper.setTo(to);  // 수신자 이메일 설정
	        helper.setSubject(subject);  // 이메일 제목 설정
	        helper.setText(body, true);  // 이메일 본문 설정 (HTML 본문 지원)

	        mailSender.send(message);  // 이메일 발송
	    } catch (Exception e) {  // MessagingException 대신 일반 예외로 처리
	        e.printStackTrace();  // 예외가 발생하면 로그로 출력하거나 적절히 처리
	    }
	}
    
    // 테스트 이메일 전송 (예시)
    public void sendTestEmail() {
        String subject = "테스트 이메일";
        String body = "이 이메일은 시스템에서 자동으로 발송된 이메일입니다.";
        sendEmailsToActiveClients(subject, body);  // 모든 활성화된 이메일로 전송
    }
}
