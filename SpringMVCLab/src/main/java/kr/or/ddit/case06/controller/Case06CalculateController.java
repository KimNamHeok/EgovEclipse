package kr.or.ddit.case06.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/case06/calcuate")
public class Case06CalculateController {
//	핸들러 3개
//	1. form 으로 연결 : get
	@GetMapping("parameter01")
	public String formUI() {
		return "case06/calForm";
	}
//	2. form-data 처리 : post
	@PostMapping
	public String formData(
		int op1
		, @RequestParam int op2
		, RedirectAttributes redirectAttributes
	) {
		int result = op1 + op2;
		redirectAttributes.addFlashAttribute("result", result);
		return "redirect:/case06/calculate/result";
	}
//	3. 연산 결과 : get
	@GetMapping("result")
	public String result(Model model) {
		log.info("result : {}", model.getAttribute("result"));
		return "case06/result";
	}
}
