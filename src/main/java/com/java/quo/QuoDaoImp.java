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
	public List<QuoModalDTO> quoChk(QuoModalDTO quoModalDTO) {
		return quoMapper.quoChk(quoModalDTO);
	}

	@Override
	public int setQuo(QuoOrderDTO quoOrderDTO) {
		return quoMapper.setQuo(quoOrderDTO);
	}

	@Override
	public int setQuoItem(QuoOrderItemDTO quoOrderItemDTO) {
		return quoMapper.setQuoItem(quoOrderItemDTO);
	}

	@Override
	public int qtyChk(int quoNo) {
		return quoMapper.qtyChk(quoNo);
	}

	@Override
	public int quoStatusChk(QuoDTO quoDTO) {
		return quoMapper.quoStatusChk(quoDTO);
	}

	@Override
	public List<QuoQtyDiffDTO> getQtyDiff(int quoNo) {
		return quoMapper.getQtyDiff(quoNo);
	}

	@Override
	public int getQuoNo(QuoDTO quoDTO) {
		return quoMapper.getQuoNo(quoDTO);
	}

	@Override
	public int del(int quoNo) {
		return quoMapper.del(quoNo);
	}

	@Override
	public int stockUpdate(int quoNo) {
		return quoMapper.stockUpdate(quoNo);
	}

	public QuoDTO findOne(QuoDTO quoDTO) {
		return quoMapper.findOne(quoDTO);
	}

	public QuoDTO findByQuoNo(int quoNo) {
		return quoMapper.findByQuoNo(quoNo);
	}

}
