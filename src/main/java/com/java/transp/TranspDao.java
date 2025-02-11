package com.java.transp;

import java.util.List;

public interface TranspDao {

	public List<TranspDTO> transList();
    public TranspInfoDTO findDriverInfo(int transpMailNo);
    public TranspInfoDTO InfoSave(TranspInfoDTO transpInfoDTO);
	public List<String> transpmail();

}
