package com.java.quo;

import java.util.List;

public interface QuoDao {
	
	public List<QuoDTO> list(QuoDTO quoDTO);
	public List<QuoModalDTO> quoModal(int no);
	public List<QuoModalDTO> quoChk(QuoModalDTO quoModalDTO);

	public int setQuo(QuoOrderDTO quoOrderDTO);
	public int setQuoItem(QuoOrderItemDTO quoOrderItemDTO);
	public int qtyChk(int quoNo);
	public int quoStatusChk(QuoDTO quoDTO);
	public List<QuoQtyDiffDTO> getQtyDiff(int quoNo);
	public int getQuoNo(QuoDTO quoDTO);
	public int del(int quoNo);
	public int stockUpdate(int quoNo);
	
	
	

}
