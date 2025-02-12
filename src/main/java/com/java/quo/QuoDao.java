package com.java.quo;

import java.util.List;

public interface QuoDao {
	
	public List<QuoDTO> list(QuoDTO quoDTO);
	public List<QuoModalDTO> quoModal(int no);

}
