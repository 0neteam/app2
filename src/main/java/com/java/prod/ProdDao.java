package com.java.prod;

import java.util.List;

import org.springframework.ui.Model;



public interface ProdDao {

    List<ProdDTO> getAllProds();  // 모든 품목 조회
    
    public int findListProds(int bizNo);

    public int addProd(ProdDTO prodDTO, Model model);  // 품목 추가
    
    public int updateProd(ProdDTO prodDTO);  // 품목 수정

    public int deleteProd(int itemCode);  // 품목 삭제
    
    public List<ProdDTO> findItemCodeProds(String itemCode); // 품목코드로 검색 조회시
    
    public List<ProdDTO> findNameProds(String name); // 품목이름으로 검색 조회시

    
}
