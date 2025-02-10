package com.java.quo;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface QuoMapper {
	
	@Select({"<script>"
			+"SELECT quoNo, quoDate, quoStatus, deliDate FROM mfr_quo"
			+"<if test='category ==\"수주번호\"'>WHERE quoNo = #{quoNo}</if> "
			+"<if test='category ==\"발주일자\"'>WHERE quoDate = #{quoDate}</if> "
			+"<if test='category ==\"납기일자\"'>WHERE deliDate = #{deliDate}</if> "
			+"<if test='category ==\"승인상태\"'>WHERE quoStatus = #{quoStatus}</if> "
			+ "</script>"
			})
	public List<QuoDTO> list(QuoDTO quoDTO);
	
//	+ "Join mfr_client on mfr_quo.bizNo = mfr_client.bizNo"
//	+"<if test='category == 2'>WHERE bizName = #{bizName}</if> "

}
