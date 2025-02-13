package com.java.prod;

import java.util.List;



public interface ProdDao {

    List<ProdDTO> getAllProds();  // 모든 품목 조회

    void addProd(ProdDTO prodDTO);  // 품목 추가

    void updateProd(ProdDTO prodDTO);  // 품목 수정

    void deleteProd(int itemCode);  // 품목 삭제

    ProdDTO getProdByItemCode(int itemCode);  // 특정 품목 조회
}
