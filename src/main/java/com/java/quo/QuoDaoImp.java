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
	

}
