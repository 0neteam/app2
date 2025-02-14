package com.java.prod;


import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

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
	public int addProd(ProdDTO prodDTO, Model model) {
        // MyBatis 매퍼의 addProd 메서드를 호출하여 품목을 DB에 추가
    	return prodMapper.addProd(prodDTO);
    }

    // 품목 수정
	public int updateProd(ProdDTO prodDTO) {
		// TODO Auto-generated method stub
		return prodMapper.updateProd(prodDTO);
	}	
    
    
    //삭제
	@Override
	public int deleteProd(int itemCode) {
		// TODO Auto-generated method stub
		return prodMapper.deleteProd(itemCode);
	}

	// 품목코드 조회
	@Override
	public List<ProdDTO> findItemCodeProds(String itemCode) {
		// TODO Auto-generated method stub
		return prodMapper.findItemCodeProds(itemCode);
	}

	// 품목이름 조회
	@Override
	public List<ProdDTO> findNameProds(String name) {
		// TODO Auto-generated method stub
		return prodMapper.findNameProds(name);
	}

	@Override
	public int findListProds(int bizNo) {
		// TODO Auto-generated method stub
		return prodMapper.findListProds(bizNo);
	}

	

	
}




	

	

    

    

