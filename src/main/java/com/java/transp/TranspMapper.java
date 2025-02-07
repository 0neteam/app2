package com.java.transp;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TranspMapper {

	@Select("SELECT * FROM mfr_transp")
	public List<TranspDTO> transList();
	
	@Insert("INSERT INTO mfr_transp_info (bizNo, driverName, driverPhone) VALUE (#{bizNo}, #{driverName}, #{driverPhone})")
	public int InfoSave(TranspInfoDTO transpInfoDTO);
	
}
