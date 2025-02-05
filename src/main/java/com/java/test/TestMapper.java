package com.java.test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {

	@Select("SELECT ${no} as no")
	public int test(int no);
	
}
