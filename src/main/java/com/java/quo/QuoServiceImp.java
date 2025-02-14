package com.java.quo;

import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuoServiceImp implements QuoService{
	
	private final QuoDao quoDao;

	@Override
	public String list(Model model, QuoSearchDTO quoSearchDTO) {
		if(quoSearchDTO == null || quoSearchDTO.getCategory() == null) {
			List<QuoDTO> quoDTOs = quoDao.list(QuoDTO.builder().build());
			model.addAttribute("result", quoDTOs);
			return "quo/quo";
		}

		if("수주번호".equals(quoSearchDTO.getCategory())) {
			try {
				int quoNo = Integer.parseInt(quoSearchDTO.getSearch());
				QuoDTO quoDTO = QuoDTO.builder().category(quoSearchDTO.getCategory()).quoNo(quoNo).build();
				List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
				model.addAttribute("result", quoDTOs);
				return "quo/quo";
			} catch (NumberFormatException e) {
				return "quo/quo";
			}
		}
		if ("발주업체".equals(quoSearchDTO.getCategory())) {
			QuoDTO quoDTO = QuoDTO.builder().category(quoSearchDTO.getCategory()).bizName(quoSearchDTO.getSearch()).build();
			List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
			model.addAttribute("result", quoDTOs);
			return "quo/quo";
		} 
		if ("발주일자".equals(quoSearchDTO.getCategory())) {
			LocalDate quoDate = LocalDate.parse(quoSearchDTO.getSearch());
			QuoDTO quoDTO = QuoDTO.builder().category(quoSearchDTO.getCategory()).quoDate(quoDate).build();
			List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
			model.addAttribute("result", quoDTOs);
			return "quo/quo";
		}
		if ("납기일자".equals(quoSearchDTO.getCategory())) {
			LocalDate deliDate = LocalDate.parse(quoSearchDTO.getSearch());
			QuoDTO quoDTO = QuoDTO.builder().category(quoSearchDTO.getCategory()).deliDate(deliDate).build();
			List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
			model.addAttribute("result", quoDTOs);
			return "quo/quo";	
		}
		if ("승인상태".equals(quoSearchDTO.getCategory())) {
			QuoDTO quoDTO = QuoDTO.builder().category(quoSearchDTO.getCategory()).quoStatus(quoSearchDTO.getSearch()).build();
			List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
			model.addAttribute("result", quoDTOs);
			return "quo/quo";
		} 

		return "quo/quo";
	}

	@Override
	public List<QuoModalDTO> quoModals(String quoNo) {
		int no = Integer.parseInt(quoNo);
		List<QuoModalDTO> quoModalDTOs = quoDao.quoModal(no);
		return quoModalDTOs;
	}

	@Override
	public String quoChk(String key, int orderNo, Model model) {
		List<QuoModalDTO> quomodalDtos = quoDao.quoChk(orderNo);
		model.addAttribute("result", quomodalDtos);
		return "quo/quoChk";
	}


}
