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
			+ "mfr_quo.dstn, "
			+ "mfr_transp_info.driverName, "
			+ "mfr_transp.transpStatus,"
			+ "mfr_transp.transpMailNo "
			+ "FROM mfr_transp "
			+ "JOIN mfr_quo ON mfr_transp.quoNo = mfr_quo.quoNo "
			+ "JOIN mfr_transp_info ON mfr_quo.bizNo = mfr_transp_info.bizNo")
	public List<TranspDTO> transList();
	
	@Select("SELECT * FROM mfr_transp_info WHERE transpMailNo = #{transpMailNo}")
	public TranspInfoDTO findDriverInfo(@Param("transpMailNo") int transpMailNo);
	
    @Select("SELECT email FROM mfr_client WHERE bizNo = #{bizNo}")
    public List<String> transpmail(@Param("bizNo") int bizNo);

    @Select("SELECT email FROM mfr_client WHERE bizNo = #{bizNo}")
    public String bizNoEmail(@Param("bizNo") int bizNo);
    
    // bizName을 모두 조회
    @Select("SELECT bizName FROM mfr_client WHERE bizNo = 129")  // 운송업체 bizNo가 129
    public List<String> getAllBizNames();

    // bizName으로 bizNo 조회
    @Select("SELECT bizNo FROM mfr_client WHERE bizName = #{bizName}")
    public int getBizNoByBizName(String bizName);
    
    @Select("SELECT quoNo FROM mfr_quo WHERE biz_no = #{bizNo}")
    public int getQuoNoByBizNo(int bizNo);
    
    @Select("SELECT orderNo, departure, dstn FROM mfr_quo WHERE quoNo = #{quoNo}")
    public TranspQuoDTO getMfrQuoByQuoNo(int quoNo);
    
    @Select("SELECT orderNo, departure, dstn FROM mfr_quo WHERE bizNo = #{bizNo}")
    public TranspQuoDTO getMfrQuoByBizNo(int bizNo); 
	
	@Insert("INSERT INTO mfr_transp_info (bizNo, driverName, driverPhone) VALUE (#{bizNo}, #{driverName}, #{driverPhone})")
	public int InfoSave(TranspInfoDTO transpInfoDTO);
	
    @Select("SELECT mfr_quo.orderNo, mfr_quo.departure, mfr_quo.dstn, " +
            "mfr_quoitem.qty, mfr_quoitem.quoItemNo, mfr_quoitem.itemCode " +
            "FROM mfr_quo " +
            "JOIN mfr_quoitem ON mfr_quo.quoNo = mfr_quoitem.quoNo " +
            "WHERE mfr_quo.bizNo = #{bizNo}")
    public TranspQuoDTO getQuoDetailsByBizNo(int bizNo);
}
	

