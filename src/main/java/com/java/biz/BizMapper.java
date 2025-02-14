package com.java.biz;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

@Mapper
public interface BizMapper {


    @Select({
            "<script>",
            "SELECT bizNo, bizName, bizType FROM mfr_client WHERE useYN = 'Y' ",
            "<if test='searchOption == \"1\"'>",
            "   AND bizName LIKE CONCAT('%', #{keyword}, '%') ",
            "</if>",
            "<if test='searchOption == \"2\"'>",
            "   AND CAST(bizNo AS CHAR) LIKE CONCAT('%', #{keyword}, '%')",
            "</if>",
            "<if test='searchOption == \"3\"'>",
            "   AND bizType LIKE CONCAT('%', #{keyword}, '%')",
            "</if>",
            "</script>"})
    public List<BizDTO> findList(BizReqDTO bizReqDTO);

    @Select("SELECT * FROM mfr_client WHERE bizNo = #{no}")
    BizDTO findOne(int no);

    @Select("SELECT * FROM mfr_api_key WHERE useYN = 'Y' AND bizNo = #{no} ORDER BY TYPE desc")
    List<BizApiKeyDTO> findByApiKey(int no);

    @Update("UPDATE mfr_client SET "
            +"      zipCode = #{zipCode},"
            +"      adr = #{adr},"
            +"      adrDetail = #{adrDetail},"
            +"      ceo = #{ceo},"
            +"      bizTel = #{bizTel},"
            +"      email = #{email} "
            +"WHERE bizNo = #{bizNo}")
    int updateBiz(BizDTO bizDTO);

    @Update(" UPDATE mfr_api_key SET "
	        +"       url = #{url}"
            +" WHERE bizNo = #{bizNo} AND type = #{type}")
    int updateApi(BizApiKeyDTO bizApiKeyDTO);

    @Update("UPDATE mfr_client SET useYN = 'N' WHERE bizNo = #{bizNo}")
    int deleteBiz(int bizNo);

    @Update("UPDATE mfr_api_key SET useYN = 'N' WHERE bizNo = #{bizNo}")
    int deleteApi(int bizNo);

    @SelectKey(statementType = StatementType.PREPARED, statement = "select last_insert_id() as bizNo", keyProperty = "bizNo", before = false, resultType = int.class)
    @Insert("INSERT INTO mfr_client "
            +"  (bizNum, bizName, bizTel, bizFax, email, ceo, bizType, zipCode, adr, adrDetail) "
            +"  VALUE "
            +"  (#{bizNum}, #{bizName}, #{bizTel}, #{bizFax}, #{email}, #{ceo}, #{bizType}, #{zipCode}, #{adr}, #{adrDetail})")
    int createBiz(BizDTO bizDTO);

    @Insert("INSERT INTO mfr_api_key "
            +"  (`bizNo`, `type`, `url`, `key`) "
            +"  VALUE "
            +"  (#{bizNo}, #{type}, #{url}, #{key})")
    int createApi(BizApiKeyDTO bizApiKeyDTO);

}
