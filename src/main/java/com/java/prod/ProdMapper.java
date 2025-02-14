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
    public  List<ProdDTO> findAllProds();
    
    // 거래처가 존재하는지 조회하는 SQL 쿼리 메서드
    @Select("SELECT 1 FROM mfr_client WHERE useYN = 'Y' AND bizNo=#{bizNo}")  // 'mfr_stock' 테이블에 맞게 수정
    public int findListProds(int bizNo);
    
    // 품목 코드를 조회하는 SQL 쿼리 메서드
    @Select("SELECT * FROM mfr_stock WHERE useYN = 'Y' AND itemCode = #{itemCode} ")  // 품목코드로 검색 조회시
    public List<ProdDTO> findItemCodeProds(String itemCode);
    
    // 품목 이름을 조회하는 SQL 쿼리 메서드
    @Select("SELECT * FROM mfr_stock WHERE useYN = 'Y' AND name LIKE CONCAT('%', #{name}, '%')")  // 품목이름으로 검색 조회시
    public List<ProdDTO> findNameProds(String name);
    
    // 품목 추가 SQL
    @Insert("INSERT INTO mfr_stock (name, qty, price) VALUES (#{name}, #{qty}, #{price})") // 품목추가 
    public int addProd(ProdDTO prodDTO);
    
    // 품목 수정 SQL
    @Update("UPDATE mfr_stock SET name=#{name}, qty=#{qty}, price=#{price} WHERE itemCode=#{itemCode}")   // 품목업데이트
    public int updateProd(ProdDTO prodDTO);
    
    // 품목 삭제 SQL
    @Update("UPDATE mfr_stock SET useYN='N' WHERE itemCode=#{itemCode}")
    public int deleteProd(int itemCode);
    
    

    
}
