package com.java.user;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;

import com.java.user.RoleDTO;
import com.java.user.UserDTO;
import com.java.user.UserRole;

@Mapper
public interface UserMapper {
	
	@Select("		SELECT a.userNo, a.`name`, d.`name` AS deptName "
			// +", b.`name` AS roleName "
			+ 	"	FROM `mfr_user` AS a                                                  "
			// + 	"	LEFT OUTER JOIN                                                       "
			// + 	"	(                                                                     "
			// + 	"		SELECT ur.`userNo`, r.`name`                                      "
			// + 	"	   FROM `mfr_role` AS r                                               "
			// + 	"		INNER JOIN `mfr_user_role` AS ur                                  "
			// + 	"		ON (r.`roleNo` = ur.`roleNo`)                                     "
			// + 	"	) AS b                                                                "
			// + 	"	ON (a.`userNo` = b.`userNo`)                                          "
			+ 	"	INNER JOIN                                                            "
			+ 	"		`mfr_dept` AS d                                                   "
			+ 	"		ON (a.`deptNo` = d.`deptNo`)                                      "
			+ 	"	WHERE a.`useYN` = 'Y' "
			// +"AND a.`userNo` <> 1         					  "
			+ " ")
	public List<UserDTO> findALL(); // 사원 전체 검색
	
	@Select("		SELECT a.userNo, a.deptNo, a.`name`, d.`name` AS deptName "
			// +", b.`name` AS roleName "
			+ 	"	FROM `mfr_user` AS a                                                  "
			// + 	"	LEFT OUTER JOIN                                                       "
			// + 	"	(                                                                     "
			// + 	"		SELECT ur.`userNo`, r.`name`                                      "
			// + 	"	   FROM `mfr_role` AS r                                               "
			// + 	"		INNER JOIN `mfr_user_role` AS ur                                  "
			// + 	"		ON (r.`roleNo` = ur.`roleNo`)                                     "
			// + 	"	) AS b                                                                "
			// + 	"	ON (a.`userNo` = b.`userNo`)                                          "
			+ 	"	INNER JOIN                                                            "
			+ 	"		`mfr_dept` AS d                                                   "
			+ 	"		ON (a.`deptNo` = d.`deptNo`)                                      "
			+ 	"	WHERE a.`useYN` = 'Y' "
			// +"AND a.`userNo` <> 1         					  "
			+	"	AND a.`userNo` LIKE CONCAT('%', #{userNo}, '%')						  ")
	public List<UserDTO> findByUserNo(String userNo); // 사번으로 사용자 검색
    
	@Select("		SELECT a.userNo, a.deptNo, a.`name`, d.`name` AS deptName "
			// +", b.`name` AS roleName "
			+ 	"	FROM `mfr_user` AS a                                                  "
			// + 	"	LEFT OUTER JOIN                                                       "
			// + 	"	(                                                                     "
			// + 	"		SELECT ur.`userNo`, r.`name`                                      "
			// + 	"	   FROM `mfr_role` AS r                                               "
			// + 	"		INNER JOIN `mfr_user_role` AS ur                                  "
			// + 	"		ON (r.`roleNo` = ur.`roleNo`)                                     "
			// + 	"	) AS b                                                                "
			// + 	"	ON (a.`userNo` = b.`userNo`)                                          "
			+ 	"	INNER JOIN                                                            "
			+ 	"		`mfr_dept` AS d                                                   "
			+ 	"		ON (a.`deptNo` = d.`deptNo`)                                      "
			+ 	"	WHERE a.`useYN` = 'Y' "
			// +"AND a.`userNo` <> 1         					  "
			+	"	AND a.`name` LIKE CONCAT('%', #{name}, '%')						  ")
	public List<UserDTO> findByName(String name); // 사원명으로 사용자 검색
    
	@Select("		SELECT a.userNo, a.deptNo, a.`name`, d.`name` AS deptName "
			// +", b.`name` AS roleName "
			+ 	"	FROM `mfr_user` AS a                                                  "
			// + 	"	LEFT OUTER JOIN                                                       "
			// + 	"	(                                                                     "
			// + 	"		SELECT ur.`userNo`, r.`name`                                      "
			// + 	"	   FROM `mfr_role` AS r                                               "
			// + 	"		INNER JOIN `mfr_user_role` AS ur                                  "
			// + 	"		ON (r.`roleNo` = ur.`roleNo`)                                     "
			// + 	"	) AS b                                                                "
			// + 	"	ON (a.`userNo` = b.`userNo`)                                          "
			+ 	"	INNER JOIN                                                            "
			+ 	"		`mfr_dept` AS d                                                   "
			+ 	"		ON (a.`deptNo` = d.`deptNo`)                                      "
			+ 	"	WHERE a.`useYN` = 'Y' "
			// +"AND a.`userNo` <> 1         					  "
			+	"	AND d.`name` LIKE CONCAT('%', #{deptName}, '%')						  ")
	public List<UserDTO> findByDept(String deptName); // 부서명으로 사용자 검색
	
	@Select("	SELECT a.* , d.name AS deptName "
			// +", b.name AS roleName "
			+ "	FROM mfr_user AS a                                  "                
			// + "	LEFT OUTER JOIN                                     "                  
			// + "	(                                                   "                  
			// + "		SELECT ur.userNo, r.name                        "              
			// + "	   FROM mfr_role AS r                               "                
			// + "		INNER JOIN mfr_user_role AS ur                  "                
			// + "		ON (r.roleNo = ur.roleNo)                       "              
			// + "	) AS b                                              "                  
			// + "	ON (a.userNo = b.userNo)                            "              
			+ "	INNER JOIN                                          "                  
			+ "		mfr_dept AS d                                   "                
			+ "		ON (a.deptNo = d.deptNo)                        "              
			+ 	"	WHERE a.`useYN` = 'Y' "
			// +"AND a.`userNo` <> 1         					  "
			+ "	AND	a.userNo = #{userNo}                            ")
	public UserDTO detailByUserNo(String userNo);  // 직원 상세화면 조회

	@Select("SELECT * FROM `mfr_user` WHERE `useYN` = 'Y' AND email = #{email}")
	public UserDTO findByUser(String email); // 이메일 검색
	
	@Select("SELECT * FROM `mfr_role` WHERE `roleNo` in (SELECT `roleNo` FROM `mfr_user_role` AS a WHERE a.`userNo` = #{no})")
	public List<RoleDTO> findByRole(int no); // 규칙검색
	
	@SelectKey(statementType = StatementType.PREPARED, statement = "select last_insert_id() as no", keyProperty = "userNo", before = false, resultType = int.class)
	@Insert("INSERT INTO `mfr_user` (`name`, `deptNo`, `pwd`, `email`, `phone`, `zipcode`, `adr`, `detail_adr`) VALUE (#{name}, #{deptNo}, #{pwd}, #{email}, #{phone}, #{zipcode}, #{adr}, #{detail_adr})")
	public int save(UserDTO user);  // 사원추가 저장
	
	@Update("UPDATE mfr_user SET pwd=#{pwd}, phone=#{phone}, zipcode=#{zipcode}, adr=#{adr}, detail_adr=#{detail_adr}, bizNo=CASE WHEN #{bizSelect} = 0 THEN NULL ELSE #{bizSelect} END WHERE userNo=#{userNo}")
	public int update(UserDTO userDTO);  // 사원정보 수정
	
	@Update("UPDATE mfr_user SET phone=#{phone}, zipcode=#{zipcode}, adr=#{adr}, detail_adr=#{detail_adr}, bizNo=CASE WHEN #{bizSelect} = 0 THEN NULL ELSE #{bizSelect} END WHERE userNo=#{userNo}")
	public int NotPwdUpdate(UserDTO user); // 패스워드를 제외한 나머지 업데이트
	
	@Update("UPDATE mfr_user SET pwd=#{pwd} WHERE email = #{email}")
	public int pwdupdate(UserDTO userDTO);  // 사원정보 비밀번호만 수정
	
	
	@Update("UPDATE mfr_user_role SET roleNo=#{selectRole} WHERE userNo=#{userNo}")
	public int updateUserRole(UserDTO user); // 사원 권한 업데이트
	
	@Insert("INSERT INTO `mfr_user_role` VALUE (#{roleNo}, #{userNo})")
	public int saveUserRole(UserRole userRole); // 사원규칙 저장
	
	@Update("UPDATE mfr_user SET `useYN` = 'N' WHERE userNo = #{UserNo}")
	public int delete(String UserNo); // 사원삭제

	@Select("SELECT b.roleNo, b.name "
			+"FROM `mfr_user_role` AS a "
			+"INNER JOIN `mfr_role` AS b "
			+"ON (a.roleNo = b.roleNo) "
			+"WHERE a.userNo = #{userNo} "
			+"ORDER BY 1 LIMIT 1 ")
	public RoleDTO findByUserNoRole(int userNo);
	
}
