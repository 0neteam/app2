package com.java.quo;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuoController {
	
	private final QuoService quoService;
	
	@GetMapping("/quo")
	public String list(Model model, @RequestParam(required = false) Map<String,	String> paramMap) {
		if(!"".equals(paramMap.get("category")) && "".equals(paramMap.get("search"))) {
			return "redirect:/quo";
		}
		return quoService.list(model, QuoSearchDTO.setDTO(paramMap));
	};
	
	@ResponseBody
	@PostMapping("/quo")
	public List<QuoModalDTO> quoDetail(@RequestParam(name="quoNo") String quoNo) {		
		return quoService.quoModals(quoNo);
	}
	
	@GetMapping("/quoChk/{key}/{orderNo}")
	public String quoChk(
			@PathVariable("key") String key, 
			@PathVariable("orderNo") Integer orderNo,
			Model model) {
		return quoService.quoChk(key, orderNo, model);
	}
	

}
