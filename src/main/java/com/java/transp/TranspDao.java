package com.java.transp;

import java.util.List;

import com.java.biz.BizDTO;
import com.java.quo.QuoModalDTO;

public interface TranspDao {

	public List<TranspDTO> transpList();
	public List<TranspDTO> transpSearch(String category, String search);
    public List<TranspModalDTO> transpModal(int no);
	public TranspInfoDTO InfoSave(TranspInfoDTO transpInfoDTO);
    public TranspInfoDTO findDriverInfo(int transpMailNo);
	public List<BizDTO> getAllBizNames();
	public String bizNoEmail(int bizNo);
	public int transpSave(TranspInfoDTO transpInfoDTO);
    public List<TranspQuoDTO> getMfrQuoByBizNo(int quoNo);
    public List<TranspQuoDTO> getTranspDetailsByQuoNo(int quoNo);
    public List<Integer> clientBizNo(int quoNo);

}
