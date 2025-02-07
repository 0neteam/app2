package com.java.transp;

import java.util.List;

public interface TranspDao {

	public List<TranspDTO> transList();
	public TranspInfoDTO InfoSave(TranspInfoDTO transpInfoDTO);
	
}
