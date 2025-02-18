package com.java.transp;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.data.repository.query.Param;

import com.java.biz.BizDTO;
import com.java.quo.QuoModalDTO;

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
	        + "t.transpDate AS transpDate "
	        + "FROM mfr_transp t "
	        + "LEFT JOIN mfr_quo q ON t.quoNo = q.quoNo "
	        + "LEFT JOIN mfr_transp_info i ON t.transpNo = i.transpNo"
	        + "</script>"})
	public List<TranspDTO> transpList();


	@Select("<script>"
	        + "SELECT t.transpNo, q.orderNo, q.departure, "
	        + "q.dstn, i.driverName, i.driverPhone, t.transpStatus, t.transpDate "
	        + "FROM mfr_transp t "
	        + "JOIN mfr_quo q ON t.quoNo = q.quoNo "
	        + "LEFT JOIN mfr_transp_info i ON t.transpNo = i.transpNo "
	        + "<where>"
	        + "<if test='category != null and search != null and search != \"\"'>"
	        + "  <choose>"
	        + "    <when test='category == \"운송번호\"'> AND t.transpNo = #{search} </when>"
	        + "    <when test='category == \"발주번호\"'> AND q.orderNo = #{search} </when>"
	        + "    <when test='category == \"출발지\"'> AND q.departure LIKE CONCAT('%', #{search}, '%') </when>"
	        + "    <when test='category == \"목적지\"'> AND q.dstn LIKE CONCAT('%', #{search}, '%') </when>"
	        + "    <when test='category == \"운송기사정보\"'> AND i.driverName LIKE CONCAT('%', #{search}, '%') </when>"
	        + "    <when test='category == \"운송상태\"'> AND t.transpStatus LIKE CONCAT('%', #{search}, '%') </when>"
	        + "  </choose>"
	        + "</if>"
	        + "</where>"
	        + "</script>")
	public List<TranspDTO> transpSearch(@Param("category") String category, @Param("search") String search);

    @Select({"<script>"
    		+ "SELECT t.transpNo, "
            + "q.orderNo, "
            + "q.departure, "
            + "q.dstn, "
            + "i.driverName, "
            + "i.driverPhone, "
            + "t.transpStatus, "
            + "i.transpMailNo, "
            + "t.transpDate "
            + "FROM mfr_transp t "
            + "JOIN mfr_quo q ON t.quoNo = q.quoNo "
            + "LEFT JOIN mfr_transp_info i ON t.transpNo = i.transpNo "
            + "WHERE t.transpNo = #{transpNo} "
            + "</script>"})
    public List<TranspModalDTO> transpModal(int transpNo);
    
	@SelectKey(statementType = StatementType.PREPARED, statement = "select last_insert_id() as transpNo", keyProperty = "transpNo", before = false, resultType = int.class)
	@Insert("INSERT INTO mfr_transp (bizNo, quoNo) VALUE (#{bizNo}, #{quoNo})")
	public int transpSave(TranspInfoDTO transpInfoDTO);

	@Insert("INSERT INTO mfr_transp_info (bizNo, transpNo, driverName, driverPhone) VALUE (#{bizNo}, #{transpNo}, #{driverName}, #{driverPhone})")
	public int InfoSave(TranspInfoDTO transpInfoDTO);
	
	@Select("SELECT * FROM mfr_transp_info WHERE transpNo = #{transpNo}")
	public TranspInfoDTO findDriverInfo(@Param("transpNo") int transpNo);

    @Select("SELECT bizNo, bizName FROM mfr_client WHERE bizNo = 129")  // 운송업체 bizNo가 129
    public List<BizDTO> getAllBizNames();
	
    @Select("SELECT email FROM mfr_client WHERE bizNo = #{bizNo}")
    public String bizNoEmail(@Param("bizNo") int bizNo);
    
    @Select(  "select mq.quoNo, "
			+ "mq.bizNo, "
			+ "mq.orderNo, "
			+ "mq.quoDate, "
			+ "mqi.itemCode, "
			+ "ms.price, "
			+ "ms.name, "
			+ "mqi.qty, "
			+ "mq.deliDate, "
			+ "mq.dstn, "
			+ "mq.departure "
			+ "from mfr_quo as mq "
			+ "left join mfr_quoitem as mqi "
			+ "on mq.quoNo = mqi.quoNo "
			+ "left join mfr_stock as ms "
			+ "on mqi.itemCode = ms.itemCode "
			+ "where mq.quoNo = #{quoNo} "
			+ "and mq.useYn = 'Y' ")
    public List<TranspQuoDTO> getMfrQuoByBizNo(int quoNo); 
    
    @Select({"SELECT q.quoNo, "
    		+ "q.orderNo, "
    		+ "qi.quoItemNo, "
    		+ "qi.itemCode, "
    		+ "qi.qty, "
    		+ "q.departure, "
    		+ "q.dstn "
    		+ "FROM mfr_transp t "
    		+ "JOIN mfr_quo q ON t.quoNo = q.quoNo "
    		+ "JOIN mfr_quoitem qi ON q.quoNo = qi.quoNo "
    		+ "WHERE t.quoNo = #{quoNo}"})
    public List<TranspQuoDTO> getTranspDetailsByQuoNo(@Param("quoNo") int quoNo);

    
    @Select("SELECT q.bizNo " +
            "FROM mfr_quo q " +
            "JOIN mfr_client c ON q.bizNo = c.bizNo " +
            "WHERE q.quoNo = #{quoNo}")
    public List<Integer> clientBizNo(int quoNo);
    
    
}

	