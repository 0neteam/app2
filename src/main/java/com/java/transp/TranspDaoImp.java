package com.java.transp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.java.biz.BizDTO;
import com.java.quo.QuoModalDTO;

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
    public List<BizDTO> getAllBizNames() {
        return transpMapper.getAllBizNames();
    }
    
	@Override
	public String bizNoEmail(int bizNo) {
		return transpMapper.bizNoEmail(bizNo);
	}

	@Override
	public int transpSave(TranspInfoDTO transpInfoDTO) {
		return transpMapper.transpSave(transpInfoDTO);
	}
	
    @Override
    public List<TranspQuoDTO> getMfrQuoByBizNo(int quoNo) {
        return transpMapper.getMfrQuoByBizNo(quoNo); // 쿼리 호출만 수행
    }

	@Override
	public List<TranspQuoDTO> getTranspDetailsByQuoNo(int quoNo) {
		return transpMapper.getTranspDetailsByQuoNo(quoNo);
	}

	@Override
	public List<Integer> clientBizNo(int quoNo) {
		return transpMapper.clientBizNo(quoNo);
	}

	@Override
	public int updateTranspStatus(int transpNo) {
		return transpMapper.updateTranspStatus(transpNo);
	}

	@Override
	public String getClientEmailByTranspNo(int transpNo) {
		return transpMapper.getClientEmailByTranspNo(transpNo);
	}


}
