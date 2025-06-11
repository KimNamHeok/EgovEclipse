package kr.or.ddit.prod.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.utils.ErrorsUtils;
import kr.or.ddit.vo.ProdVO;

//@WebServlet("/prod/prodUpdate.do")
@Controller
@RequestMapping("/prod/prodUpdate.do")
public class ProdUpdateController {
	@Autowired
	private ProdService service;
	private final static String MODELNAME = "prod";
	@Autowired
	private ErrorsUtils errorsUtils;
	/**
	 * 수정 form 제공
	 */
	@GetMapping
	public String formUI(@RequestParam String what, Model model) {
		if(!model.containsAttribute(MODELNAME)) {
			ProdVO prod = service.readProd(what).get();
			model.addAttribute("prod", prod);
		}
		
		model.addAttribute("action", "update");
		
		return "prod/prodForm";
	}
	
	/**
	 *
	 * form 으로 입력받은 데이터 처리
	 */
	@PostMapping
	public String formProcess(
		String what,
		@Validated(UpdateGroup.class) @ModelAttribute(MODELNAME) ProdVO prod,
		BindingResult errors,
		RedirectAttributes redirectAttributes
	){
		String lvn;
		if(!errors.hasErrors()) {
			service.modifyProd(prod);
			// PRG 패턴
			lvn = "redirect:/prod/prodDetail.do?what="+prod.getProdId();
		}else {
			redirectAttributes.addFlashAttribute(MODELNAME, prod);
			
			redirectAttributes.addFlashAttribute("errors", errorsUtils.errorsToMap(errors));
			lvn= "redirect:/prod/prodUpdate.do?what="+what;
		}
		return lvn;
	}
}
