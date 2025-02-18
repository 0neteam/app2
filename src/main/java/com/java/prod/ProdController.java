package com.java.prod;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.common.JwtToken;
import com.java.quo.QuoDao;
import com.java.quo.QuoResOrderDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ProdController {
	
	private final JwtToken jwtToken;

    private final ProdService prodService ;
   
    // 모든 재고 목록 페이지
    
    @GetMapping("/prod")
    public String getAllProds(Model model) {
        return prodService.getAllProds(model);  // 재고 목록 페이지로 리턴
    }

    // 품목 추가 페이지
    @ResponseBody
	@PostMapping("/prod/add")
    public Map<String, String> addProd(@ModelAttribute ProdDTO prodDTO, Model model) {
    	
    	System.out.println("/////////////ProdDTO////////////////");
    	System.out.println("ProdDTO : " + prodDTO);
          // 서비스에서 품목 추가
    	
    	int status = prodService.addProd(prodDTO, model);
    	
		Map<String, String> response = new HashMap<>();
				
		if (status == 1) {  // 이메일 존재 여부를 체크하는 로직
	        response.put("status", "OK");	        
	    } else {
	        response.put("status", "FAIL");
	    }
			    
        return response;  // 추가 후 품목 목록 페이지로 리다이렉트
    }


    // 품목 삭제 처리
    @ResponseBody
    @PostMapping("/prod/delete")
    public Map<String, String> deleteProd(@ModelAttribute ProdDTO prodDTO) {
        
    	System.out.println("/////////////ProdDTO////////////////");
    	System.out.println("ProdDTO : " + prodDTO);
        
    	int status = prodService.deleteProd(prodDTO.getItemCode());  // 서비스에서 품목 삭제
    	
		Map<String, String> response = new HashMap<>();
				
		if (status == 1) {  // 이메일 존재 여부를 체크하는 로직
	        response.put("status", "OK");	        
	    } else {
	        response.put("status", "FAIL");
	    }
		
        return response;  // 삭제 후 품목 목록 페이지로 리다이렉트
   
    }
    
    
    // 품목 검색 처리   
    @ResponseBody
    @PostMapping("/prod/search")
    public List<ProdDTO> searchProd(@ModelAttribute ProdDTO prodDTO) {
        
    	System.out.println("/////////////ProdDTO////////////////");
    	System.out.println("ProdDTO : " + prodDTO);
    	
    	ProdDTO NullprodDTO = new ProdDTO();
        
    	List<ProdDTO> prodDTO_result = prodService.searchProd(prodDTO);  // 서비스에서 품목 삭제
    	
    	if (prodDTO_result != null) {
    		return prodDTO_result;
    	}
    	else {
    		return null;
    	}
    	
    }
    
    
    
    // 품목 수정 처리
    @ResponseBody
    @PostMapping("/prod/update")
    public Map<String, String> updateProd(@ModelAttribute ProdDTO prodDTO) {
    	
    	System.out.println("/////////////ProdDTO////////////////");
    	System.out.println("ProdDTO : " + prodDTO);
    	
    	Map<String, String> response = new HashMap<>();
    	
        int status = prodService.updateProd(prodDTO);  // 서비스에서 품목 수정   
        
        System.out.println("status : " + status);

        if (status == 1) {  // 이메일 존재 여부를 체크하는 로직
	        response.put("status", "OK");	        
	    } else {
	        response.put("status", "FAIL");
	    }
		
        return response;  // 수정 저장 후 ajax 요청으로 리턴
    }

    @CrossOrigin(origins = "*")
    @ResponseBody
    @PostMapping("/api/list")
    public QuoResOrderDTO listProd(@RequestHeader("Authorization") String key) {
        QuoResOrderDTO resDTO = QuoResOrderDTO.builder().status(false).build();

        try {
            int bizNo = Integer.parseInt(jwtToken.getBizNo(key));
            int status = prodService.findListProds(bizNo);
            if (status == 1) { // 거래처 확인 성공
                List<Map<String, Object>> resultList = new ArrayList<>();
                for(ProdDTO prodDTO : prodService.getListProds()) {
                    Map<String, Object> resultMap = new HashMap<>();
                    resultMap.put("itemCode", prodDTO.getItemCode());
                    resultMap.put("name", prodDTO.getName());
                    resultList.add(resultMap);
                }
                resDTO.setData(resultList);
                resDTO.setStatus(true);
            } 
        } catch (NumberFormatException e) {
            // 숫자 파싱 실패 시 예외 처리
            log.info("NumberFormatException: {}", e.getMessage());
            resDTO.setMsg("유효한 거래처 정보가 아닙니다.");
        } catch (Exception e) {
            // 그 외 다른 예외 처리
            log.info("Exception: {}", e.getMessage());
            resDTO.setMsg("적절한 요청이 아닙니다.");
        }
        
        return resDTO;        
    }

    


}
