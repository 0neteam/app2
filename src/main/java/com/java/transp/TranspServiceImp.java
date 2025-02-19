package com.java.transp;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.java.biz.BizDTO;
import com.java.quo.QuoModalDTO;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TranspServiceImp implements TranspService {

	private final TranspDao transpDao;
	private final JavaMailSender mailSender;
	
    @Value("${spring.mail.username}")
    private String emailFrom;

	@Override
	public String transpList(Model model, HttpServletRequest req) {
		List<TranspDTO> transpList = transpDao.transpList();
		System.out.println(transpList);
		model.addAttribute("transpList", transpList);
		return "transp/transp";
	}

	@Override
	public String transpSearch(Model model, HttpServletRequest req) {
		String category = req.getParameter("category");
		String search = req.getParameter("search");
		List<TranspDTO> transpList = transpDao.transpSearch(category, search);
		model.addAttribute("transpList", transpList);
		return "transp/transp";
	}

	@Override
	public List<TranspModalDTO> transpModals(String transpNo) {
		int no = Integer.parseInt(transpNo);
		List<TranspModalDTO> transpModalDTOs = transpDao.transpModal(no);
	    return transpModalDTOs;
	}


	@Override
	public String InfoSave(TranspInfoDTO transpInfoDTO) {
	    try {
	        if (transpDao.InfoSave(transpInfoDTO) == null) {
	            return "redirect:/transpEmail/" + transpInfoDTO.getQuoNo() + "/" + transpInfoDTO.getBizNo() + "/" + transpInfoDTO.getTranspNo();
	        }
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	    }
	    return "redirect:/";  // 실패시 홈 페이지 등으로 리다이렉트
	}

	
	@Override
	public TranspInfoDTO getTranspInfo(int transpMailNo) {
	    return transpDao.findDriverInfo(transpMailNo);
	}
	
	@Override
    public List<BizDTO> getAllBizNames() {
	    return transpDao.getAllBizNames();
    }
	
	@Override
	public Boolean sendEmailToClient(int bizNo, int quoNo, int transpNo) {
	    // bizNo로 이메일을 가져옵니다.
	    String emailTo = transpDao.bizNoEmail(bizNo);  // bizNo를 이용해 이메일 조회
	    if (emailTo == null || emailTo.isEmpty()) {
	        return false;  // 이메일이 없으면 실패
	    }

	    List<TranspQuoDTO> transpQuoDTOs = transpDao.getTranspDetailsByQuoNo(quoNo);
	    if (transpQuoDTOs.isEmpty()) {
	        return false;  // 수주 정보가 없으면 실패
	    }

	    // 첫 번째 수주 정보를 사용
	    TranspQuoDTO firstQuo = transpQuoDTOs.get(0);
	    
	    // bizNo 리스트에서 첫 번째 bizNo를 가져옵니다
	    List<Integer> clientBizNo = transpDao.clientBizNo(quoNo);
	    if (clientBizNo.isEmpty()) {
	        return false;  // clientBizNo가 비어 있으면 실패
	    }
	    Integer clientBizNoValue = clientBizNo.get(0);  // 첫 번째 bizNo를 사용
	    // 이메일 본문 (링크만 포함)
	    String emailContent = "<h2>운송 정보</h2>"
//	            + "<p>발주 코드: " + firstQuo.getOrderNo() + "</p>"
//	            + "<p>품목 코드: " + firstQuo.getQuoItemNo() + "</p>"
//	            + "<p>품목 수량: " + firstQuo.getQty() + "</p>"
//	            + "<p>출발지: " + firstQuo.getDeparture() + "</p>"
//	            + "<p>도착지: " + firstQuo.getDstn() + "</p>"
	            + "<p>운송자 정보를 입력하려면 아래 링크를 클릭하세요.</p>"
	            + "<p><a href='http://localhost:8080/transpEmail/" + quoNo + "/" + clientBizNoValue  + "/" + transpNo + "'>운송자 정보 입력</a></p>";

		try {
			// 이메일 전송 로직
			MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(emailFrom); // 보내는 이메일 주소 설정
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
    public String processEmailSending(Integer quoNo, Integer bizNo, Model model) {
		
		String message = null;

		// 만약 quoNo가 null이면 오류 메시지 출력
        if (quoNo == null) {
            message = "수주번호가 필요합니다.";
        } else {
			model.addAttribute("quoNo", quoNo);
			model.addAttribute("bizNames", getAllBizNames()); 

			if(bizNo != null) {
				// transp 생성
				TranspInfoDTO transpInfoDTO = TranspInfoDTO.builder().bizNo(bizNo).quoNo(quoNo).build();
				if(transpDao.transpSave(transpInfoDTO) == 1) {
					int transpNo = transpInfoDTO.getTranspNo();
					// 실제 이메일 전송 처리
					boolean emailSent = sendEmailToClient(bizNo, quoNo, transpNo);  // 이메일 전송 메서드 호출
	
					// 이메일 전송 성공 여부에 따라 반환할 메시지 결정
					if (emailSent) {
						message = "이메일 전송 성공";
					} else {
						message = "이메일 전송 실패";
					}
				}

			}
		}
		model.addAttribute("message", message);
		return "transp/transpSendEmail";
    }
	
    
	public List<TranspQuoDTO> getMfrQuoByBizNo(int quoNo) {
		return transpDao.getMfrQuoByBizNo(quoNo);
	}

    @Override
    public Boolean cancelTransp(int transpNo) {
        // 운송 상태를 '운송 취소'로 업데이트
        int updatedRows = transpDao.updateTranspStatus(transpNo);
        if (updatedRows > 0) {
            // 이메일 발송
            String clientEmail = transpDao.getClientEmailByTranspNo(transpNo);
            if (clientEmail != null) {
                sendCancelEmail(clientEmail);  // 이메일 전송 메서드 호출
                return true;
            }
        }
        return false;  // 실패 시
    }

    private void sendCancelEmail(String clientEmail) {
        // 이메일 본문 설정
        String emailContent = "<h2>운송 취소 안내</h2>"
                + "<p>고객님께서 요청하신 운송이 취소되었습니다.</p>";

        try {
            // 이메일 전송 설정
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // 발신자 이메일 주소 설정
            helper.setFrom(emailFrom);
            // 수신자 이메일 주소 설정
            helper.setTo(clientEmail);
            // 이메일 제목 설정
            helper.setSubject("운송 취소 안내");
            // HTML 이메일 본문 설정
            helper.setText(emailContent, true);

            // 이메일 전송
            mailSender.send(message);
            System.out.println("운송 취소 이메일 전송 성공: " + clientEmail);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("운송 취소 이메일 전송 실패: " + clientEmail);
        }
    }
}
