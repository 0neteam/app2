package com.java.prod;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.java.user.UserDTO;



public interface ProdService {

	
	 public String getAllProds(Model model);   // 모든 품목을 조회하는 메서드

	 void addProd(ProdDTO prodDTO);  // 품목을 추가하는 메서드

    void updateProd(ProdDTO prodDTO);  // 품목을 수정하는 메서드

    void deleteProd(int itemCode);  // 품목을 삭제하는 메서드
}
