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
	public List<String> transpmail() {
		return transpMapper.transpmail();
	}




}
