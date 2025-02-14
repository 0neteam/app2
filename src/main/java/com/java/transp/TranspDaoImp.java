package com.java.transp;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TranspDaoImp implements TranspDao {

	private final TranspMapper transpMapper;
	
	public TranspDaoImp(TranspMapper transpMapper) {
		this.transpMapper = transpMapper;
	}
	
	@Override
	public List<TranspDTO> transList() {
		return transpMapper.transList();
	}

	@Override
	public TranspInfoDTO findDriverInfo(int transpMailNo) {
		return transpMapper.findDriverInfo(transpMailNo);
	}
	
	@Override
	public TranspInfoDTO InfoSave(TranspInfoDTO transpInfoDTO) {
		int status = transpMapper.InfoSave(transpInfoDTO);
		return (status == 1) ? transpInfoDTO : null;
	}

	@Override
	public List<String> transpmail(int bizNo) {
		return transpMapper.transpmail(bizNo);
	}

	@Override
	public String bizNoEmail(int bizNo) {
		return transpMapper.bizNoEmail(bizNo);
	}

    // ✅ bizName 전체 조회 수정
    @Override
    public List<String> getAllBizNames() {
        return transpMapper.getAllBizNames();
    }

    // ✅ bizName으로 bizNo 조회 수정
    @Override
    public int getBizNoByBizName(String bizName) {
        return transpMapper.getBizNoByBizName(bizName);
    }

	@Override
	public int getQuoNoByBizNo(int bizNo) {
        return transpMapper.getQuoNoByBizNo(bizNo); // 필요 시 구현
	}

    @Override
    public TranspQuoDTO getMfrQuoByBizNo(int bizNo) {
        return transpMapper.getMfrQuoByBizNo(bizNo); // 쿼리 호출만 수행
    }

	@Override
	public TranspQuoDTO getMfrQuoByQuoNo(int quoNo) {
	    return transpMapper.getMfrQuoByQuoNo(quoNo);  // quoNo를 이용해 데이터를 조회하여 반환
	}

	@Override
	public TranspQuoDTO getQuoDetailsByBizNo(int bizNo) {
	    return transpMapper.getQuoDetailsByBizNo(bizNo);  // bizNo를 이용해 데이터 조회
	}

}
