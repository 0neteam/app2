package com.java.user;

import java.util.List;

public interface UserDao {

	public List<UserDTO> findALL();
	
	public List<UserDTO> findByUserNo(String userNo);
	
	public List<UserDTO> findByName(String name);
	
	public List<UserDTO> findByDept(String deptName);
	
	public UserDTO findByUser(String email);
	
	public RoleDTO findByRole(int no);
	
	public int save(UserDTO user);
	
	public int saveUserRole(UserRole userRole);
	
	public int delete(String UserNo);
}
