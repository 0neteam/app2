package com.java.quo;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.java.biz.BizDao;
import com.java.common.JwtToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuoServiceImp implements QuoService{
	
	private final QuoDao quoDao;
	private final BizDao bizDao;
	private final JwtToken jwtToken;

	@Override
	public String list(Model model, QuoSearchDTO quoSearchDTO) {
		QuoDTO quoDTO = QuoDTO.builder().build();
		if(quoSearchDTO != null) {
			if ("발주일자".equals(quoSearchDTO.getCategory()) || "납기일자".equals(quoSearchDTO.getCategory())) {
				quoSearchDTO.setSearch( quoSearchDTO.getSearch().replace("-", "") );
			}
			quoDTO = QuoDTO.builder().category(quoSearchDTO.getCategory()).search(quoSearchDTO.getSearch()).build();
		}
		List<QuoDTO> quoDTOs = quoDao.list(quoDTO);
		model.addAttribute("result", quoDTOs);
		return "quo/quo";
	}

	@Override
	public List<QuoModalDTO> quoModals(String orderNo) {
		int no = Integer.parseInt(orderNo);
		List<QuoModalDTO> quoModalDTOs = quoDao.quoModal(no);
		return quoModalDTOs;
	}

	@Override
	public String quoChk(String key, int orderNo, Model model) {
		try {
			int bizNo = Integer.parseInt( jwtToken.getBizNo(key) );
			QuoModalDTO quoModalDTO = QuoModalDTO.builder().orderNo(orderNo).bizNo(bizNo).build();
			QuoDTO quoDTO = QuoDTO.builder().orderNo(orderNo).bizNo(bizNo).build();
			model.addAttribute("result", quoDao.quoChk(quoModalDTO));
			model.addAttribute("quo", quoDao.findOne(quoDTO));
			model.addAttribute("biz", bizDao.findOne(bizNo));
			return "quo/quoChk";
		} catch (NumberFormatException e) {
			model.addAttribute("error", "신뢰할수 없는 정보 입니다.");
			return "error";
		}
	}

	/******
	 * 
	 * 
	 * 
	 *******/

	@Override
	public QuoResOrderDTO setQuo(String key, Integer status, QuoReqOrderDTO quoReqOrderDTO) {
		QuoResOrderDTO quoResOrderDTO = QuoResOrderDTO.builder().status(true).build();
		try {
			int bizNo = Integer.parseInt( jwtToken.getBizNo(key) );

			if(status == 1) { // 발주 대기 단계
				QuoOrderDTO quoOrderDTO = quoReqOrderDTO.getQuoOrderDTO();
				List<QuoOrderItemDTO> quoOrderItems = quoReqOrderDTO.getQuoOrderItem();

				// Quo 넣기
				quoOrderDTO.setBizNo(bizNo);
				quoOrderDTO.setDeparture("서울특별시 서대문구 연세로 8-1 7층");
				if( quoDao.setQuo(quoOrderDTO) == 1 ) {

					if(quoOrderItems != null) {
						boolean itemChk = true;

						// QuoItem 넣기 (반복문)
						for(QuoOrderItemDTO quoOrderItemDTO : quoOrderItems) {
							quoOrderItemDTO.setQuoNo(quoOrderDTO.getQuoNo());
							if(quoDao.setQuoItem(quoOrderItemDTO) == 0) {
								itemChk = false;
								break;
							}
						}

						if(itemChk) {
							String quoStatus = "견적검토";
							if(quoDao.qtyChk(quoOrderDTO.getQuoNo()) > 0) {
								quoStatus = "견적취소";
								List<QuoQtyDiffDTO> quoQtyDiffDTO = quoDao.getQtyDiff(quoOrderDTO.getQuoNo());
								quoResOrderDTO.setStatus(false);
								quoResOrderDTO.setData(quoQtyDiffDTO);
								quoResOrderDTO.setMsg("수량이 부족한걸 어찌 할까?");
							}
							quoDao.quoStatusChk(QuoDTO.builder().quoNo(quoOrderDTO.getQuoNo()).quoStatus(quoStatus).build());
						}
					}

				}
			} 

		} catch (NumberFormatException e) {
			quoResOrderDTO.setStatus(false);
			quoResOrderDTO.setMsg("유효한 사용자 정보가 없습니다.");
		}
		return quoResOrderDTO;
	}

	@Override
	public String del(int quoNo) {
		if(quoDao.del(quoNo) > 0) {
			return "redirect:/quo";
		}
		return "redirect:/access-denied";
	}

	public QuoResOrderDTO quoState(Integer status, Integer orderNo, Integer bizNo) {
		boolean state = false;
		String msg = "";
		if(status == 2) { // 발주 취소 단계
			QuoDTO quoDTO = QuoDTO.builder().orderNo(orderNo).bizNo(bizNo).build();
			int quoNo =  quoDao.getQuoNo(quoDTO);
			if(quoNo > 0) {
				state = (quoDao.quoStatusChk(QuoDTO.builder().quoNo(quoNo).quoStatus("발주취소").build()) == 1) ? true : false;
				msg = state ? "발주 취소 되었습니다." : "발주 취소 중 오류 발생 되었습니다.";
			}
		}
		if(status == 3) { // 발주 확정 단계
			QuoDTO quoDTO = QuoDTO.builder().orderNo(orderNo).bizNo(bizNo).build();
			int quoNo =  quoDao.getQuoNo(quoDTO);
			if(quoNo > 0) {
				int sts = quoDao.quoStatusChk(QuoDTO.builder().quoNo(quoNo).quoStatus("발주확정").build());
				sts += quoDao.stockUpdate(quoNo);
				state = (sts > 1) ? true : false;
				msg = state ? "발주 확정이 성공적으로 처리 되었습니다." : "발주 확정 중 오류 발생 되었습니다.";
			}
		}

		return QuoResOrderDTO.builder().status(state).msg(msg).build();
	}
}
