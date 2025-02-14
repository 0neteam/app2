package com.java.quo;

import java.util.List;

import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class QuoDaoImp implements QuoDao{
	
	private final QuoMapper quoMapper;

	@Override
	public List<QuoDTO> list(QuoDTO quoDTO) {
		return quoMapper.list(quoDTO);
	}

	@Override
	public List<QuoModalDTO> quoModal(int no) {
		return quoMapper.quoModal(no);
	}

	@Override
	public List<QuoModalDTO> quoChk(int orderNo, int bizNo) {
		return quoMapper.quoChk(orderNo, bizNo);
	}
	

}
