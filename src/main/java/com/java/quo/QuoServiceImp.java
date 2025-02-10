package com.java.quo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuoServiceImp implements QuoService{
	
	private final QuoDao quoDao;

	@Override
	public String list(Model model, HttpServletRequest req) {

		String category = req.getParameter("category");
		String search = req.getParameter("search");
		if(category == null) {
			return "quo/quo";
			
		}else if(category.equals("수주번호")) {
			try {
				int quoNo = Integer.parseInt(search);
				QuoDTO quoDTO = QuoDTO.builder().category(category).quoNo(quoNo).build();
				List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
				model.addAttribute("result", quoDTOs);
				return "quo/quo";
			} catch (NumberFormatException e) {
				return "quo/quo";
			}
		}else if (category.equals("발주업체")) {
			QuoDTO quoDTO = QuoDTO.builder().category(category).bizName(search).build();
			List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
			model.addAttribute("result", quoDTOs);
			return "quo/quo";
		}else if (category.equals("발주일자")) {
			LocalDate quoDate = LocalDate.parse(search);
			QuoDTO quoDTO = QuoDTO.builder().category(category).quoDate(quoDate).build();
			List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
			model.addAttribute("result", quoDTOs);
			return "quo/quo";
		}else if (category.equals("납기일자")) {
			LocalDate deliDate = LocalDate.parse(search);
			QuoDTO quoDTO = QuoDTO.builder().category(category).deliDate(deliDate).build();
			List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
			model.addAttribute("result", quoDTOs);
			return "quo/quo";	
		}else if (category.equals("승인상태")) {
			QuoDTO quoDTO = QuoDTO.builder().category(category).quoStatus(search).build();
			List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
			model.addAttribute("result", quoDTOs);
			return "quo/quo";
		} else {
		return "quo/quo";
		}
	}
}
