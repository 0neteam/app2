package com.java.user;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.java.user.MyUserDTO;
import com.java.user.RoleDTO;
import com.java.user.UserDTO;
import com.java.user.UserRole;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService, UserDetailsService {

	private final UserDao userDAO;
	
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserDTO user = userDAO.findByUser(email);
		if(user != null) {
			RoleDTO role = userDAO.findByRole(user.getUserNo());
			
			if(role != null) {
				return new MyUserDTO(user, role);
			}
		}
		
		return null;
	}
	
	@Override
	public String list(Model model, String searchOption, String searchKeyword) {	
		
		List<UserDTO> userDTO = null;
		
		if (searchOption == null || searchKeyword == null || searchKeyword.isEmpty()) {
			userDTO = userDAO.findALL();  // 전달 받은 값이 없으면 전체 목록 조회		
			System.out.println("///////////////////////findALL////////////////////");
        }

        // 검색옵션에 따라 Mapper로 쿼리 실행
        switch (searchOption) {
            case "userNo":
            	userDTO = userDAO.findByUserNo(searchKeyword);  // 사번으로 조회            
            	System.out.println("///////////////////////findByUserNo////////////////////");
            	break;
            case "name":
            	userDTO = userDAO.findByName(searchKeyword);    // 사원명으로 조회        
            	System.out.println("///////////////////////findByName////////////////////");
            	break;
            case "dept":
            	userDTO = userDAO.findByDept(searchKeyword);    // 부서명으로 조회    
            	System.out.println("///////////////////////findByDept////////////////////");
            	break;
            default:
            	userDTO = userDAO.findALL();  // 전부 해당 안될시 기본값 전체 조회
            	System.out.println("///////////////////////전부 해당 안될시 기본값 전체 조회////////////////////");
            	
        }
        System.out.println("///////////////////////userDTO////////////////////");
        System.out.println("userDTO : " + userDTO);
		model.addAttribute("rs", userDTO);
		
		return "user/list";
	}
	
	public String save(UserDTO user) {
		user.setPwd(passwordEncoder.encode(user.getPwd()));
		user.setDeptNo(2); //기본 일반 유저로 등록		
		
		int status = userDAO.save(user);
		if(status == 1) {			
			status = userDAO.saveUserRole(UserRole.builder().userNo(user.getUserNo()).roleNo(2).build());
			if(status == 1) {
				
				// 로그인 여부 확인
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	            if (authentication != null && authentication.isAuthenticated()) {	// 사용자 인증 상태 확인                
	                return "redirect:/user/list"; // 인증된 사용자는 /user/list로 리디렉션
	            } else {	                
	                return "redirect:/signIn"; // 인증되지 않은 사용자는 /signIn으로 리디렉션
	            }
			}
		}
		return "redirect:/signUp";
	}
	
	// 이메일 중복 체크
	public UserDTO email_du_chk(String email) {		
		return userDAO.findByUser(email);		
	}
	
	// 직원삭제
	@Override
	public boolean delete(String userNo) {
		try {			
			int status = userDAO.delete(userNo);
			if(status == 1) return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
