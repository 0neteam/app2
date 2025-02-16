package com.java.quo;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

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
			+ "where mq.useYN = 'Y' "
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
			+ "where mq.orderNo = #{no} "
			+ "and mq.useYn = 'Y' ")
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

	@SelectKey(statementType = StatementType.PREPARED, statement = "select last_insert_id() as quoNo", keyProperty = "quoNo", before = false, resultType = int.class)
	@Insert("INSERT INTO mfr_quo "
			+"(orderNo, bizNo, departure, dstn, deliDate, orderDate) "
			+"VALUE "
			+"(#{orderNo}, #{bizNo}, #{departure}, #{dstn}, #{deliDate}, #{orderDate})")
	public int setQuo(QuoOrderDTO quoOrderDTO);

	@Insert("INSERT INTO mfr_quoitem "
			+"(quoNo, itemCode, qty) "
			+"VALUE "
			+"(#{quoNo}, #{itemCode}, #{qty})")
	public int setQuoItem(QuoOrderItemDTO quoOrderItemDTO);

	@Select("SELECT sum(case when (c.qty - b.qty) < 0 then 1 ELSE 0 END) AS qty "
			+"FROM mfr_quoitem AS b "
			+"INNER JOIN mfr_stock AS c "
			+"ON (b.itemCode = c.itemCode) "
			+"WHERE b.quoNo = #{quoNo}")
	public int qtyChk(int quoNo);

	@Update("UPDATE mfr_quo SET quoStatus = #{quoStatus} WHERE quoNo = #{quoNo}")
	public int quoStatusChk(QuoDTO quoDTO);

	@Select("SELECT c.itemCode, c.name, c.qty AS mfrQty, b.qty AS stgQty, (c.qty - b.qty) AS diff "
			+"FROM mfr_quoitem AS b "
			+"INNER JOIN mfr_stock AS c "
			+"ON (b.itemCode = c.itemCode) "
			+"WHERE b.quoNo = #{quoNo} "
			+"AND (c.qty - b.qty) < 0 "
			+"ORDER BY 1")
	public List<QuoQtyDiffDTO> getQtyDiff(int quoNo);

	@Select("SELECT quoNo FROM mfr_quo WHERE orderNo = #{orderNo} AND bizNo = #{bizNo}")
	public int getQuoNo(QuoDTO quoDTO);

	@Update("UPDATE mfr_quo SET useYN = 'N' WHERE quoNo = #{quoNo}")
	public int del(int quoNo);
	
	@Update("UPDATE mfr_quoitem AS a "
			+ " INNER JOIN mfr_stock AS b "
			+ "    ON a.itemCode = b.itemCode "
			+ "SET b.qty = (b.qty - a.qty)  "
			+ "WHERE a.quoNo = #{quoNo}" )
	public int stockUpdate(int quoNo);
}
