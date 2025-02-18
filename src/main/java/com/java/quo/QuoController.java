package com.java.quo;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.common.JwtToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuoController {
	
	private final QuoService quoService;
	
	@GetMapping("/quo")
	public String list(Model model, @RequestParam(required = false) Map<String,	String> paramMap) {
		return quoService.list(model, QuoSearchDTO.setDTO(paramMap));
	};
	
	@ResponseBody
	@PostMapping("/quo")
	public List<QuoModalDTO> quoDetail(@RequestParam(name="quoNo") String quoNo) {		
		return quoService.quoModals(quoNo);
	}
	
	// /quoChk/3/pK2hGWjw1+U40NqqEZQmaQt7SSdmRKHn7WvhEYrlzQTUX2qBgDZ6ifVeTNmi1uy6mbgBf8P3ugRXoQVNbyFoCcGd89H2XOysMgRgca35HjDODUuBw+RxzZp1zXjhS1fbdc8ai4tKD+lpUQS7EqjrTkOhDSVP6Eho77cH+Aa+6AesWYRbuFfI12ozEHV5dgbUfu/i0AK33frft2F1fvADRlYrLJRYKF2A/SAUi1XtEjwQs8t2FmdjtaH14R24rSCXIz5eWSxuikANW0lnN0SeOW3Hi4ylfjCcNJNzn/S+HKN6QlAmNkUGm1zMZtwcKbk01ZSAawxLZ9N3npMtx2r1uw==
	@GetMapping("/quoChk/{orderNo}/{key}")
	public String quoChk(
			@PathVariable("orderNo") Integer orderNo,
			@PathVariable("key") String key, 
			Model model) {
		return quoService.quoChk(key, orderNo, model);
	}
	
	@CrossOrigin(origins = "*")
	@ResponseBody
	@PostMapping("/api/order/{status}")
	public QuoResOrderDTO setQuoDTO(
			@PathVariable("status") Integer status,
			@RequestHeader("Authorization") String key,
			@RequestBody(required = false) QuoReqOrderDTO quoReqOrderDTO) {
		return quoService.setQuo(key, status, quoReqOrderDTO);
	}
	
	@GetMapping("/quo/del")
	public String del(@RequestParam(name="quoNo") Integer quoNo) {
		return quoService.del(quoNo);
	}
	
	

	
	
	
	
	
	
	
	
	// 거래처 등록시 생성 되는 KEY 테스트용
	private final JwtToken jwtToken;

	@ResponseBody
	@GetMapping("/quoKey/{bizNo}")
	public String quoKey(@PathVariable("bizNo") String bizNo) {
		return jwtToken.setToken(bizNo);
	}

}
