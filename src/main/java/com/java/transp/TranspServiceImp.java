package com.java.transp;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class TranspServiceImp implements TranspService {

	private final TranspDao transpDao;
	private final JavaMailSender mailSender;
	
    @Value("${spring.mail.username}")
    private String emailFrom;

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

    // 이메일 전송 로직을 서비스 계층에 추가

	@Override
    public Boolean sendEmailToClient(int bizNo) {
        // bizNo로 이메일을 가져옵니다.
        String emailTo = transpDao.bizNoEmail(bizNo);  // bizNo를 이용해 이메일 조회
        TranspQuoDTO quoDTO = transpDao.getMfrQuoByBizNo(bizNo);
        
        if (emailTo == null || emailTo.isEmpty()) {
            return false;  // 이메일이 없으면 실패
        }

        // 이메일 본문 (링크만 포함)
        String emailContent = "<h2>운송 정보</h2>"
        		+ "<p>발주 코드: " + quoDTO.getOrderNo() + "</p>"
                + "<p>품목 코드: " + quoDTO.getItemCode() + "</p>"
                + "<p>품목 수량: " + quoDTO.getQty() + "</p>"
                + "<p>출발지: " + quoDTO.getDeparture() + "</p>"
                + "<p>도착지: " + quoDTO.getDstn() + "</p>"
                + "<p>운송자 정보를 입력하려면 아래 링크를 클릭하세요.</p>"
                + "<p><a href='http://localhost:8080/transpEmail/" + bizNo + "'>운송자 정보 입력</a></p>";

        // 이메일 전송 로직
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("from-email@example.com"); // 보내는 이메일 주소 설정
            helper.setTo(emailTo);  // 조회한 이메일 주소로 설정
            helper.setSubject("운송자 정보 입력 링크");
            helper.setText(emailContent, true);  // HTML 설정
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


	@Override
	public String bizNoEmail(int bizNo) {
		return transpDao.bizNoEmail(bizNo);
	}

	@Override
    public List<String> getAllBizNames() {
	    return transpDao.getAllBizNames();
    }

    // ✅ bizName으로 bizNo 조회
	@Override
    public int getBizNoByBizName(String bizName) {
        return transpDao.getBizNoByBizName(bizName);
    }

	@Override
	public TranspQuoDTO getMfrQuoByBizNo(int bizNo) {
		return transpDao.getMfrQuoByBizNo(bizNo);
	}

    @Override
    public String processEmailSending(int bizNo, String carrier) {
        // 예시 로직: 이메일 전송 작업을 처리하고 결과 메시지 반환
        if (carrier == null || carrier.isEmpty()) {
            return "운송업체를 선택해주세요.";
        }

        // 실제 이메일 전송 로직 (비즈니스 로직 처리)
        boolean emailSent = sendEmailToClient(bizNo);  // 이메일 전송 메소드 호출

        // 이메일 전송 성공 여부에 따라 메시지 반환
        if (emailSent) {
            return "이메일 전송 성공";
        } else {
            return "이메일 전송 실패";
        }
    }

}
