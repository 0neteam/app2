package com.java.transp;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class TranspController {
	
	private final TranspService transpService;

	@GetMapping("/transp")
	public String transpList(Model model, HttpServletRequest req) {
		return transpService.transpList(model, req);
	}
 
    @GetMapping("/transp/search")
    public String transpSearch(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "search", required = false) String search,
            Model model,
            HttpServletRequest req) {

        return transpService.transpSearch(model, req);
    }
    
    @GetMapping("/transp/modal")
    public String transpModal(@RequestParam("transpNo") String transpNo, Model model) {
        List<TranspModalDTO> transpDetail = transpService.transpModals(transpNo);
        model.addAttribute("transpDetail", transpDetail);
        return "transp/transp";
    }



    @GetMapping("/transpEmail/{bizNo:[0-9]+}")
    public String TranspEmail(@PathVariable(name = "bizNo") Integer bizNo, Model model) {
    	model.addAttribute("bizNo", bizNo);
        return "transp/transpEmail";
    };
    
    @GetMapping("/transpInfo/{transpMailNo}")
    public String getTranspInfo(@PathVariable("transpMailNo") Integer transpMailNo, Model model, HttpServletRequest req) {
    	transpService.transpList(model, req);
        TranspInfoDTO driverInfo = transpService.getTranspInfo(transpMailNo);
        model.addAttribute("driverInfo", driverInfo);
        return "transp/transp";
    }
  
    @PostMapping("/InfoSave")
    public String InfoSave(HttpServletRequest req) {
    	return transpService.InfoSave(req);
    }
    
    @GetMapping("/transpSendEmail")
    public String sendEmail(@RequestParam(name = "carrier", required = false) String carrier, 
                            @RequestParam(name = "quoNo", required = false) Integer quoNo, 
                            Model model) {
        // bizNo가 129인 운송업체 이름만 가져옴
        List<String> bizNames = transpService.getAllBizNames();
        // 모델에 bizNames 추가
        model.addAttribute("bizNames", bizNames);  

        // 만약 운송업체를 선택하지 않으면 메시지 출력
        if (carrier == null || carrier.isEmpty()) {
            model.addAttribute("message", bizNames.isEmpty() ? "운송업체가 존재하지 않습니다." : "운송업체를 선택해주세요.");
            return "transp/transpSendEmail";
        }

        // 만약 quoNo가 null이면 오류 메시지 출력
        if (quoNo == null) {
            model.addAttribute("message", "수주번호가 필요합니다.");
            return "transp/transpSendEmail";
        }

        // 이메일 전송 처리 결과 반환
        String message = transpService.processEmailSending(129, carrier, quoNo);
        model.addAttribute("message", message);
        return "transp/transpSendEmail";  
    }

    
    
}
