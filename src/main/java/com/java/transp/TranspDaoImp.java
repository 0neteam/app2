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
	public int InfoSave(TranspInfoDTO transpInfoDTO) {
		return transpMapper.InfoSave(transpInfoDTO);
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
	public int updateTranspStatus(TranspDTO transpDTO) {
		return transpMapper.updateTranspStatus(transpDTO);
	}

	@Override
	public String getClientEmailByTranspNo(int transpNo) {
		return transpMapper.getClientEmailByTranspNo(transpNo);
	}

	public TranspDTO findTransInfo(int transpNo) {
		return transpMapper.findTransInfo(transpNo);
	}

}
