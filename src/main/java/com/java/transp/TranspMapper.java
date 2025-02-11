package com.java.transp;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

@Mapper
public interface TranspMapper {

	@Select("SELECT mfr_transp.transpNo,"
			+ "mfr_quo.orderNo, "
			+ "mfr_quo.departure,"
			+ "stg.stg_order.dstn, "
			+ "mfr_transp_info.driverName, "
			+ "mfr_transp.transpStatus,"
			+ "mfr_transp.transpMailNo "
			+ "FROM mfr_transp "
			+ "JOIN mfr_quo ON mfr_transp.quoNo = mfr_quo.quoNo "
			+ "JOIN stg.stg_order ON mfr_quo.orderNo = stg.stg_order.orderNo "
			+ "JOIN mfr_transp_info ON mfr_quo.bizNo = mfr_transp_info.bizNo")
	public List<TranspDTO> transList();
	
	@Select("SELECT * FROM mfr_transp_info WHERE transpMailNo = #{transpMailNo}")
	public TranspInfoDTO findDriverInfo(@Param("transpMailNo") int transpMailNo);
	
	@Select("SELECT email FROM mfr_client WHERE useYN = 'Y'")
	public List<String> transpmail();
	
	@Insert("INSERT INTO mfr_transp_info (bizNo, driverName, driverPhone) VALUE (#{bizNo}, #{driverName}, #{driverPhone})")
	public int InfoSave(TranspInfoDTO transpInfoDTO);
	
}
