package com.java.transp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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



    @GetMapping("/transpEmail/{quoNo:[0-9]+}/{bizNo:[0-9]+}/{transpNo:[0-9]+}")
    public String TranspEmail(
        @PathVariable(name = "quoNo") Integer quoNo, 
        @PathVariable(name = "bizNo") Integer bizNo, 
        @PathVariable(name = "transpNo") Integer transpNo, 
        Model model) {
        
        model.addAttribute("quoNo", quoNo);
        model.addAttribute("bizNo", bizNo);
        model.addAttribute("transpNo", transpNo);
        model.addAttribute("quo", transpService.getMfrQuoByBizNo(quoNo));  // 견적 정보 전달
        
        return "transp/transpEmail";  // 초기 화면은 폼과 함께 보여짐
    }

    
    @GetMapping("/transpInfo/{transpMailNo}")
    public String getTranspInfo(@PathVariable("transpMailNo") Integer transpMailNo, Model model, HttpServletRequest req) {
    	transpService.transpList(model, req);
        TranspInfoDTO driverInfo = transpService.getTranspInfo(transpMailNo);
        model.addAttribute("driverInfo", driverInfo);
        return "transp/transp";
    }
  
    @PostMapping("/InfoSave")
    public String InfoSave(@ModelAttribute TranspInfoDTO transpInfoDTO) {
        System.out.println("________________" + transpInfoDTO);
        
        // 운송자 정보 저장 처리
        transpService.InfoSave(transpInfoDTO);
        
        // 폼 제출 후 갱신된 견적 정보를 전달하여 페이지 리다이렉트
        return "redirect:/transpEmail/" + transpInfoDTO.getQuoNo() + "/" + transpInfoDTO.getBizNo() + "/" + transpInfoDTO.getTranspNo();
    }


    
    @GetMapping("/transpSendEmail")
    public String sendEmail(Model model,
        @RequestParam(name = "quoNo") Integer quoNo, 
        @RequestParam(name = "bizNo", required = false) Integer bizNo) {
        return transpService.processEmailSending(quoNo, bizNo, model);
    }

    
    @PostMapping("/transp/cancel/{transpNo}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cancelTransp(@PathVariable("transpNo") int transpNo) {
        Map<String, Object> response = new HashMap<>();

        try {
            // TranspService의 cancelTransp 메서드를 호출하여 운송 취소 처리
            Boolean success = transpService.cancelTransp(transpNo);

            if (success) {
                response.put("success", true);
                response.put("message", "운송 취소가 완료되었습니다.");
            } else {
                response.put("success", false);
                response.put("message", "운송 취소에 실패했습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "운송 취소 처리 중 오류가 발생했습니다.");
        }

        return ResponseEntity.ok(response);
    }
}
