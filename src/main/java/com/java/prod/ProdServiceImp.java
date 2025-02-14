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
    
    public List<ProdDTO> getListProds() {
    	
        return prodDAO.getAllProds();  // 모든 품목을 가져오는 DAO 메서드
    }

	public int addProd(ProdDTO prodDTO, Model model) {
		
		return prodDAO.addProd(prodDTO, model);
    }
	

	@Override
	public int updateProd(ProdDTO prodDTO) {
		// TODO Auto-generated method stub
		return prodDAO.updateProd(prodDTO);
	}	
    	
    	
	public int deleteProd(int itemCode) {
    return prodDAO.deleteProd(itemCode);  // 품목을 삭제하는 DAO 메서드
    }
    	

	@Override
	public List<ProdDTO> searchProd(ProdDTO prodDTO) {
		// TODO Auto-generated method stub
		
		if(prodDTO.getSearchType().equals("code")) { // 품목코드 조회시
			return prodDAO.findItemCodeProds(prodDTO.getSearchInput());
		} 
		
		if(prodDTO.getSearchType().equals("name")) { // 품목명 조회시
			return prodDAO.findNameProds(prodDTO.getSearchInput());			
		} 
		
		return null;
	}


	@Override
	public int findListProds(int bizNo) {
		// TODO Auto-generated method stub
		return prodDAO.findListProds(bizNo);
	}

	

	
}
