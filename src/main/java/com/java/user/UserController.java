package com.java.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.user.UserDTO;
import com.java.user.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;

	@GetMapping("/signUp")
	public String userCreate() {
		
		return "/user/create";
	}
	
	
	// 사원 상세정보
	@PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 접근 가능하도록 설정
	@GetMapping("/user/detail")
	public String userDetail(Model model, HttpServletRequest req) {		
				
		return userService.detailByUserNo(model, req);
	}
		
	
	// edit 사원 정보 불러오기
	@PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 접근 가능하도록 설정	
	@GetMapping("/user/edit")
	public String userEdit(Model model, HttpServletRequest req) {
		
		return userService.editByUserNo(model, req);
	}
	
	
	
	// 사원 삭제
	@ResponseBody
	@PostMapping("/user/delete")
	public Map<String, String> userDel(@RequestParam Map<String, String> request) {
		
		String userNo = request.get("userNo");
		
		Map<String, String> response = new HashMap<>();
		
		if (userService.delete(userNo)) {  // 이메일 존재 여부를 체크하는 로직
	        response.put("status", "OK");	        
	    } else {
	        response.put("status", "FAIL");
	    }
	    return response;
	    
	}	
	
	
	// update 사원 정보 업데이트
	@PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 접근 가능하도록 설정	
	@PostMapping("/user/update")
	@ResponseBody
	public ResponseEntity<Map<String, String>> userUpdate(@ModelAttribute UserDTO user) {
		
		System.out.println("/////////////Controller user/update userDTO/////////////////////////");
		System.out.println("userDTO : " + user);
		System.out.println("//////////////////////////////////////////////////////////");
		
		Map<String, String> response = new HashMap<>();
		
		String status = userService.update(user);
		
		response.put("status", status);
		response.put("userNo", Integer.toString(user.getUserNo()));
		
	    //response.put("redirectUrl", "/user/edit?userNo=" + user.getUserNo());
	    
	    
		return ResponseEntity.ok(response);
	}

	
	
	// 직원추가가입
	@PostMapping("/signUp")
	public String signUp(@ModelAttribute UserDTO user) {
		return userService.save(user);
	}	
	
	// 사용자 리스트 조회
	@PreAuthorize("isAuthenticated()")  // 로그인한 사용자만 접근 가능하도록 설정
	@GetMapping("/user/list")
	public String list(@RequestParam(value = "searchOption", required = false) String searchOption,
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            Model model) {
		
		// searchOption이 null일 경우 기본값 설정 (예: "userNo")
        if (searchOption == null) {
            searchOption = "";  // 기본 검색 옵션 (예: 사번)
        }

        // searchKeyword가 null일 경우 빈 문자열로 설정
        if (searchKeyword == null) {
            searchKeyword = "";
        }
		
		return userService.list(model, searchOption, searchKeyword);
	}
		
	
	// 이메일 중복 체크로직1
	@ResponseBody
	@PostMapping("/user/create/checkemail")	
	public Map<String, String> checkEmailDuplicate(@RequestParam Map<String, String> request) {
		
	    String email = request.get("email");  // JSON 형식으로 전달된 이메일 값 추출
	    
	    Map<String, String> response = new HashMap<>();
	    if (emailExists(email)) {  // 이메일 존재 여부를 체크하는 로직
	        response.put("status", "FAIL");	        
	    } else {
	        response.put("status", "OK");
	    }
	    return response;
	}
	
	// 이메일 중복 체크로직2
	private boolean emailExists(String email) {	
	    
		UserDTO userDTO = userService.email_du_chk(email);
		
		if(userDTO != null) {
			return true;  // 서버에 같은 이름의 email 계정이 존재하는경우	
		}
		else 
		{
			return false;  // 존재하지 않는 이메일 이름인 경우	
		}
		
	    
	}
	
	
	
}
