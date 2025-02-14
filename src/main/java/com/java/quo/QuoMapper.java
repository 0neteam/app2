package com.java.quo;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuoMapper {
	
	@Select({"<script>"
			+"SELECT mq.quoNo, "
			+ "mq.orderNo, "
			+ "mq.bizNo, "
			+ "mq.quoDate, "
			+ "mq.quoStatus, "
			+ "mq.dstn, "
			+ "mq.deliDate, "
			+ "mc.bizName, "
			+ "mc.bizNum, "
			+ "mc.adr "
			+" FROM mfr_quo AS mq "
			+" left JOIN mfr_client AS mc "
			+" ON mq.bizNo = mc.bizNo "
			+ "where mq.useYN == 'Y' "
			+"<if test='category == \"수주번호\"'>and mq.quoNo = #{quoNo}</if> "
			+"<if test='category == \"발주업체\"'>and mc.bizName = #{bizName}</if> "
			+"<if test='category == \"발주일자\"'>and mq.quoDate = #{quoDate}</if> "
			+"<if test='category == \"납기일자\"'>and mq.deliDate = #{deliDate}</if> "
			+"<if test='category == \"승인상태\"'>and mq.quoStatus = #{quoStatus}</if> "
			+ "</script>"
			})
	public List<QuoDTO> list(QuoDTO quoDTO);
	
	@Select(  "select mq.quoNO, "
			+ "mq.bizNo, "
			+ "mq.quoDate, "
			+ "mqi.itemCode, "
			+ "ms.price, "
			+ "ms.name, "
			+ "mqi.qty, "
			+ "mq.deliDate, "
			+ "mq.dstn "
			+ "from mfr_quo as mq "
			+ "left join mfr_quoitem as mqi "
			+ "on mq.quoNo = mqi.quoNo "
			+ "left join mfr_stock as ms "
			+ "on mqi.itemCode = ms.itemCode "
			+ "where mq.quoNo = #{no} ")
	public List<QuoModalDTO> quoModal(int no);
	
	@Select("select mq.quoNO, "
			+ "mq.bizNo, "
			+ "mq.quoDate, "
			+ "mqi.itemCode, "
			+ "ms.price, "
			+ "ms.name, "
			+ "mqi.qty, "
			+ "mq.deliDate, "
			+ "mq.dstn "
			+ "from mfr_quo as mq "
			+ "left join mfr_quoitem as mqi "
			+ "on mq.quoNo = mqi.quoNo "
			+ "left join mfr_stock as ms "
			+ "on mqi.itemCode = ms.itemCode "
			+ "WHERE mq.orderNo = #{orderNo} "
			+ "  AND mq.bizNo = #{bizNo} ")
	public List<QuoModalDTO> quoChk(QuoModalDTO quoModalDTO);

}
