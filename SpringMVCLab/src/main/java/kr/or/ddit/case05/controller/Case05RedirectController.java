package kr.or.ddit.case05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/case05")
public class Case05RedirectController {
	@GetMapping("start02")
	public String start02(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("modelInfo", "전달데이터");
		// PRG pattern
		return "redirect:/case05/dest02";
	}
	// addFlashAttribute -> FlashMap 에 데이터 저장
	// --> redirect
	// Model 생성 -> FlashMap 에 저장되어 있는 flash attribute --> Model 로 이동 --> flash attribute 삭제 
	//   위에 전체 구조를 관리해주는게 flashMap manger
	@GetMapping("dest02")
	public String dest02(Model model) {
		if(model.containsAttribute("modelInfo")) {
			log.info("model : {}", model.getAttribute("modelInfo"));
		}
		return "case05/view01";
	}
}
