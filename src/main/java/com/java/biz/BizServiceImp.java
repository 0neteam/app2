package com.java.biz;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@RequiredArgsConstructor
@Service
public class BizServiceImp implements BizService {

    private final BizDao bizDao;

	@Override
	public String list(Model model, Map<String, String> paramMap) {
		BizReqDTO bizReqDTO = BizReqDTO.builder().build();
		if(paramMap != null) {
			String searchOption = paramMap.get("searchOption");
			String keyword = paramMap.get("keyword");
			bizReqDTO = BizReqDTO.builder().searchOption(searchOption).keyword(keyword).build();
		}
		System.out.println(bizReqDTO);
		List<BizDTO> bizDtos = bizDao.findList(bizReqDTO);
		System.out.println("++++++++++++++++++ " + bizDtos);
		model.addAttribute("bizList", bizDtos);
		return "biz/list";
	}

	@Override
	public String detail(Model model, HttpServletRequest req) {
		try{
			int no = Integer.parseInt(req.getParameter("bizNo"));
			BizDTO biz1 = bizDao.findOne(no);
			model.addAttribute("bizResult", biz1);
			return "biz/detail";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}

	public BizResDTO update(Map<String, String> paramMap) {
		BizDTO bizDTO = BizDTO.setBizDTO(paramMap);
        if(bizDTO != null) {
			if(bizDao.update(bizDTO)) {
				return BizResDTO.builder().status(true).build();
			}
		}
			
		return BizResDTO.builder().status(false).build();
	}

	public BizResDTO delete(int bizNo) {
		if(bizDao.delete(bizNo)) {
			return BizResDTO.builder().status(true).build();
		}
		return BizResDTO.builder().status(false).build();
	}

	public BizResDTO create(Map<String, String> paramMap) {
		BizDTO bizDTO = BizDTO.setClientDTO(paramMap);
		if(bizDTO != null) {
			if(bizDao.create(bizDTO)) {
				return BizResDTO.builder().status(true).build();
			}
		}
			
		return BizResDTO.builder().status(false).build();
	}

}
