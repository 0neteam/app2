package com.java.prod;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.java.user.UserDTO;



public interface ProdService {

	
	 public String getAllProds(Model model);   // 모든 품목을 조회하는 메서드
	 
	 public int findListProds(int bizNo); // 거래처가 존재하는지 복호화한 bizNo값으로 조회. 

	 public int addProd(@ModelAttribute ProdDTO prodDTO, Model model);  // 품목을 추가하는 메서드
	
	 public int updateProd(ProdDTO prodDTO);  // 품목을 수정하는 메서드

	 public int deleteProd(int itemCode);  // 품목을 삭제하는 메서드
    
	 public List<ProdDTO> searchProd(ProdDTO prodDTO); // 품목코드, 품목명으로 조회시 ProdDTO 필드에 저장하여 사용.
    
	 public List<ProdDTO> getListProds(); // 품목 리스트 조회 요청시(창고 -> 제조사)
    
}
