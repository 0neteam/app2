package com.java.quo;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuoMapper {
	
	@Select({"<script>"
			+"SELECT mq.quoNo, "
			+ "mq.quoDate, "
			+ "mq.quoStatus, "
			+ "mq.deliDate, "
			+ "mc.bizName "
			+"  FROM mfr_quo AS mq "
			+" INNER JOIN mfr_client AS mc "
			+"    ON mq.bizNo = mc.bizNo "
//			+ "where mq.quoNo == 'Y' "
			+"<if test='category == \"수주번호\"'>where mq.quoNo = #{quoNo}</if> "
			+"<if test='category == \"발주업체\"'>where mc.bizName = #{bizName}</if> "
			+"<if test='category == \"발주일자\"'>where mq.quoDate = #{quoDate}</if> "
			+"<if test='category == \"납기일자\"'>where mq.deliDate = #{deliDate}</if> "
			+"<if test='category == \"승인상태\"'>where mq.quoStatus = #{quoStatus}</if> "
			+ "</script>"
			})
	public List<QuoDTO> list(QuoDTO quoDTO);
	
	@Select({"<script>"
			+ "select mq.quoNO, "
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
			+ "where mq.quoNo = #{no} "
			+ "</script>"
	})
	public List<QuoModalDTO> quoModal(int no);
//	+ "left join mfr_client as mc "
//	+ "on mq.bizNo = mc.bizNo "
//	+ "mc.bizNum, "
//	+ "mc.bizName, "
//	+ "mc.adr, "
	

}
