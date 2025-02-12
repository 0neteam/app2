package com.java.transp;

import java.util.List;

public interface TranspDao {

	public List<TranspDTO> transList();
    public TranspInfoDTO findDriverInfo(int transpMailNo);
    public TranspInfoDTO InfoSave(TranspInfoDTO transpInfoDTO);
	public List<String> transpmail(int bizNo);
	public String bizNoEmail(int bizNo);
	public List<String> getAllBizNames(); 
	public int getBizNoByBizName(String bizName);
    public int getQuoNoByBizNo(int bizNo);
    public TranspQuoDTO getMfrQuoByBizNo(int bizNo);
    public TranspQuoDTO getMfrQuoByQuoNo(int quoNo);
    public TranspQuoDTO getQuoDetailsByBizNo(int bizNo);

}
