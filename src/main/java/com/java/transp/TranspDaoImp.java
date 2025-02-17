package com.java.transp;

import java.util.List;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class TranspDaoImp implements TranspDao {

	private final TranspMapper transpMapper;

	@Override
	public List<TranspDTO> transpList() {
		return transpMapper.transpList();
	}

	@Override
	public List<TranspDTO> transpSearch(String category, String search) {
		return transpMapper.transpSearch(category, search);
	}

	@Override
    public List<TranspModalDTO> transpModal(int no) {
		return transpMapper.transpModal(no);
	}
	
	@Override
	public TranspInfoDTO InfoSave(TranspInfoDTO transpInfoDTO) {
		int status = transpMapper.InfoSave(transpInfoDTO);
		return (status == 1) ? transpInfoDTO : null;
	}
	

	@Override
	public TranspInfoDTO findDriverInfo(int transpMailNo) {
		return transpMapper.findDriverInfo(transpMailNo);
	}
	
    @Override
    public List<String> getAllBizNames() {
        return transpMapper.getAllBizNames();
    }
    
	@Override
	public String bizNoEmail(int bizNo) {
		return transpMapper.bizNoEmail(bizNo);
	}
	
    @Override
    public TranspQuoDTO getMfrQuoByBizNo(int bizNo) {
        return transpMapper.getMfrQuoByBizNo(bizNo); // 쿼리 호출만 수행
    }
}
