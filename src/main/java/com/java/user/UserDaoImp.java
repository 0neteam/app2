package com.java.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.java.user.UserDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserDaoImp implements UserDao {

	final UserMapper userMapper;
	
	@Override
	public List<UserDTO> findALL() {
		// TODO Auto-generated method stub
		return userMapper.findALL();
	}

	@Override
	public UserDTO findByUser(String email) {
		// TODO Auto-generated method stub
		return userMapper.findByUser(email);
	}

	@Override
	public int save(UserDTO user) {
		// TODO Auto-generated method stub
		return userMapper.save(user);
	}

	@Override
	public int saveUserRole(UserRole userRole) {
		// TODO Auto-generated method stub
		return userMapper.saveUserRole(userRole);
	}

	@Override
	public List<RoleDTO> findByRole(int no) {
		// TODO Auto-generated method stub
		return userMapper.findByRole(no);
	}

	@Override
	public int delete(String UserNo) {
		// TODO Auto-generated method stub
		return userMapper.delete(UserNo);
	}

	@Override
	public List<UserDTO> findByUserNo(String userNo) {
		// TODO Auto-generated method stub
		return userMapper.findByUserNo(userNo);
	}

	@Override
	public List<UserDTO> findByName(String name) {
		// TODO Auto-generated method stub
		return userMapper.findByName(name);
	}

	@Override
	public List<UserDTO> findByDept(String deptName) {
		// TODO Auto-generated method stub
		return userMapper.findByDept(deptName);
	}

	@Override
	public UserDTO detailByUserNo(String userNo) {
		// TODO Auto-generated method stub
		return userMapper.detailByUserNo(userNo);
	}

	@Override
	public int NotPwdUpdate(UserDTO user) {
		// TODO Auto-generated method stub
		return userMapper.NotPwdUpdate(user);
	}

	@Override
	public int update(UserDTO user) {
		// TODO Auto-generated method stub
		return userMapper.update(user);
	}

	@Override
	public int updateUserRole(UserDTO user) {
		// TODO Auto-generated method stub
		return userMapper.updateUserRole(user);
	}

//	@Override
//	public int authCodeUpdate(UserDTO userDTO) {
//		// TODO Auto-generated method stub
//		return userMapper.authCodeUpdate(userDTO);
//	}

	@Override
	public int pwdupdate(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return userMapper.pwdupdate(userDTO);
	}

	public RoleDTO findByUserNoRole(int userNo) {
		return userMapper.findByUserNoRole(userNo);
	}

}
