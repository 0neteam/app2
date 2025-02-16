package com.java.transp;

import java.util.List;

public interface TranspDao {

	public List<TranspDTO> transpList();
	public List<TranspDTO> transpSearch(String category, String search);
    public List<TranspModalDTO> transpModal(int no);
	public TranspInfoDTO InfoSave(TranspInfoDTO transpInfoDTO);
    public TranspInfoDTO findDriverInfo(int transpMailNo);
	public List<String> getAllBizNames();
	public String bizNoEmail(int bizNo);
    public TranspQuoDTO getMfrQuoByBizNo(int bizNo);
}
