package com.java.prod;

import org.springframework.stereotype.Service;

import org.springframework.ui.Model;

import java.util.List;

@Service

public class ProdServiceImp implements ProdService {

    private final ProdDao prodDAO;  // DAO 객체를 주입받아 사용

    // 생성자 주입 (안전한 방식)
    public ProdServiceImp(ProdDao prodDAO) {
        this.prodDAO = prodDAO;
    }

    public String getAllProds(Model model) {
    	List<ProdDTO> prodDTO = prodDAO.getAllProds();
    	
    
    	
    	model.addAttribute("prodList", prodDTO);
        return "prod/prod";  // 모든 품목을 가져오는 DAO 메서드
    }

    	public void addProd(ProdDTO prodDTO) {
        prodDAO.addProd(prodDTO);  // 품목을 추가하는 DAO 메서드
    }

    	public void updateProd(ProdDTO prodDTO) {
        prodDAO.updateProd(prodDTO);  // 품목을 수정하는 DAO 메서드
    }

    	public void deleteProd(int itemCode) {
        prodDAO.deleteProd(itemCode);  // 품목을 삭제하는 DAO 메서드
    }

	
}
