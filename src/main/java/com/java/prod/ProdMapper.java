package com.java.prod;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


import java.util.List;

@Mapper
public interface ProdMapper {
    
    // 모든 품목을 조회하는 SQL 쿼리 메서드
    @Select("SELECT * FROM mfr_stock WHERE useYN = 'Y'")  // 'mfr_stock' 테이블에 맞게 수정
    List<ProdDTO> findAllProds();
    
    // 품목 추가 SQL
    @Insert("INSERT INTO mfr_stock (name, qty, price, useYN, regDate) " +
            "VALUES (#{name}, #{qty}, #{price}, #{useYN}, #{regDate})")  // 'mfr_stock'에 맞게 수정
    void addProd(ProdDTO prodDTO);
    
    // 품목 수정 SQL
    @Update("UPDATE mfr_stock SET name = #{name}, qty = #{qty}, price = #{price}, useYN = #{useYN} " +
            "WHERE itemCode = #{itemCode}")  // 'mfr_stock'에 맞게 수정
    void updateProd(ProdDTO prodDTO);
    
    // 품목 삭제 SQL
    @Delete("DELETE FROM mfr_stock WHERE itemCode = #{itemCode}")  // 'mfr_stock'에 맞게 수정
    void deleteProd(int itemCode);
}
