package com.java.transp;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

@Mapper
public interface TranspMapper {

    @Select({"<script>"
    		+ "SELECT t.transpNo AS transpNo, "
            + "q.orderNo AS orderNo, "
            + "q.departure AS departure, "
            + "q.dstn AS dstn, "
            + "i.driverName AS driverName, "
            + "i.driverPhone AS driverPhone, "
            + "t.transpStatus AS transpStatus, "
            + "t.transpMailNo AS transpMailNo,"
            + "t.transpDate AS transpDate "
            + "FROM mfr_transp t "
            + "JOIN mfr_quo q ON t.quoNo = q.quoNo "
            + "JOIN mfr_transp_info i ON t.transpMailNo = i.transpMailNo"
            + "</script>"})
    public List<TranspDTO> transpList();

    @Select("<script>"
            + "SELECT mfr_transp.transpNo, mfr_quo.orderNo, mfr_quo.departure, "
            + "mfr_quo.dstn, mfr_transp_info.driverName, mfr_transp.transpStatus "
            + "FROM mfr_transp "
            + "JOIN mfr_quo ON mfr_transp.quoNo = mfr_quo.quoNo "
            + "JOIN mfr_transp_info ON mfr_transp.transpMailNo = mfr_transp_info.transpMailNo "  // 수정: transpMailNo로 연결
            + "<where>"
            + "<if test='category != null and search != null'>"
            + "  <choose>"
            + "    <when test='category == \"운송번호\"'> AND mfr_transp.transpNo = #{search} </when>"
            + "    <when test='category == \"발주번호\"'> AND mfr_quo.orderNo = #{search} </when>"
            + "    <when test='category == \"출발지\"'> AND mfr_quo.departure LIKE CONCAT('%', #{search}, '%') </when>"
            + "    <when test='category == \"목적지\"'> AND mfr_quo.dstn LIKE CONCAT('%', #{search}, '%') </when>"
            + "    <when test='category == \"운송기사정보\"'> AND mfr_transp_info.driverName LIKE CONCAT('%', #{search}, '%') </when>"
            + "    <when test='category == \"운송상태\"'> AND mfr_transp.transpStatus LIKE CONCAT('%', #{search}, '%') </when>"
            + "  </choose>"
            + "</if>"
            + "</where>"
            + "</script>")
    public List<TranspDTO> transpSearch(@Param("category") String category, @Param("search") String search);

    @Select({"<script>"
    		+ "SELECT mfr_transp.transpNo, "
            + "mfr_quo.orderNo, "
            + "mfr_quo.departure, "
            + "mfr_quo.dstn, "
            + "mfr_transp_info.driverName, "
            + "mfr_transp_info.driverPhone, "
            + "mfr_transp.transpStatus, "
            + "mfr_transp.transpMailNo, "
            + "mfr_transp.transpDate "
            + "FROM mfr_transp "
            + "JOIN mfr_quo ON mfr_transp.quoNo = mfr_quo.quoNo "
            + "JOIN mfr_transp_info ON mfr_quo.bizNo = mfr_transp_info.bizNo "
            + "WHERE mfr_transp.transpNo = #{no} "
            + "</script>"})

    public List<TranspModalDTO> transpModal(int no);
    
	@Insert("INSERT INTO mfr_transp_info (bizNo, driverName, driverPhone) VALUE (#{bizNo}, #{driverName}, #{driverPhone})")
	public int InfoSave(TranspInfoDTO transpInfoDTO);
	
	@Select("SELECT * FROM mfr_transp_info WHERE transpMailNo = #{transpMailNo}")
	public TranspInfoDTO findDriverInfo(@Param("transpMailNo") int transpMailNo);

    @Select("SELECT bizName FROM mfr_client WHERE bizNo = 129")  // 운송업체 bizNo가 129
    public List<String> getAllBizNames();
	
    @Select("SELECT email FROM mfr_client WHERE bizNo = #{bizNo}")
    public String bizNoEmail(@Param("bizNo") int bizNo);
    
    @Select("SELECT orderNo, departure, dstn FROM mfr_quo WHERE bizNo = #{bizNo}")
    public TranspQuoDTO getMfrQuoByBizNo(int bizNo); 
}

	