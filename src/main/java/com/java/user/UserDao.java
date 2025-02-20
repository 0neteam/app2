package com.java.user;

import java.util.List;

public interface UserDao {

	public List<UserDTO> findALL();
	
	public List<UserDTO> findByUserNo(String userNo);
	
	public List<UserDTO> findByName(String name);
	
	public List<UserDTO> findByDept(String deptName);
	
	public UserDTO findByUser(String email);
	
	public List<RoleDTO> findByRole(int no);
	
	public int save(UserDTO user);
	
	public int NotPwdUpdate(UserDTO user);
	
	public int update(UserDTO user);
	
	public int updateUserRole(UserDTO user);
	
	public int saveUserRole(UserRole userRole);
	
	public int delete(String UserNo);
	
	public UserDTO detailByUserNo(String userNo);
	
//	public int authCodeUpdate(UserDTO userDTO);
	
	public int pwdupdate(UserDTO userDTO);

	public RoleDTO findByUserNoRole(int userNo);
	
}
