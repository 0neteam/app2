package com.java.prod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ProdController {

    @Autowired
    private ProdService prodService;

    // 모든 재고 목록 페이지
    @GetMapping("/prod")
    public String getAllProds(Model model) {
        return prodService.getAllProds(model);  // 재고 목록 페이지로 리턴
    }

    // 품목 추가 페이지
    @PostMapping("/prod/add")
    public String addProd(@ModelAttribute ProdDTO prodDTO, Model model) {
        prodService.addProd(prodDTO);  // 서비스에서 품목 추가
        model.addAttribute("message", "품목이 성공적으로 추가되었습니다.");
        return "redirect:/prod";  // 추가 후 품목 목록 페이지로 리다이렉트
    }


    // 품목 삭제 처리
    @GetMapping("/prod/delete/{itemCode}")
    public String deleteProd(@PathVariable("itemCode") int itemCode) {
        prodService.deleteProd(itemCode);  // 서비스에서 품목 삭제
        return "redirect:/prod";  // 삭제 후 품목 목록 페이지로 리다이렉트
   
    }
 // 품목 수정 처리
    @PostMapping("/prod/update")
    public String updateProd(@ModelAttribute ProdDTO prodDTO, Model model) {
        prodService.updateProd(prodDTO);  // 서비스에서 품목 수정
        model.addAttribute("message", "품목이 성공적으로 수정되었습니다.");
        return "redirect:/prod";  // 수정 후 품목 목록 페이지로 리다이렉트
    }




}
