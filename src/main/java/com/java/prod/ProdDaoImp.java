package com.java.prod;


import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProdDaoImp implements ProdDao {

    private final ProdMapper prodMapper;  // ProdMapper 의존성 주입

    // 모든 품목 조회    
    @Override
    public List<ProdDTO> getAllProds() {
        // MyBatis에서 자동으로 쿼리 실행 및 결과 반환
        return prodMapper.findAllProds(); // MyBatis가 자동으로 처리
    }

    // 품목 추가
    @Override
    public void addProd(ProdDTO prodDTO) {
        // MyBatis 매퍼의 addProd 메서드를 호출하여 품목을 DB에 추가
    	prodMapper.addProd(prodDTO);
    }

    // 품목 수정
    @Override
    public void updateProd(ProdDTO prodDTO) {
        
      prodMapper.updateProd(prodDTO);
    }
    //삭제
	@Override
	public void deleteProd(int itemCode) {
		// TODO Auto-generated method stub
		prodMapper.deleteProd(itemCode);
	}

	@Override
	public ProdDTO getProdByItemCode(int itemCode) {
		// TODO Auto-generated method stub
		return null;
	}



	
	}

	

    

    

