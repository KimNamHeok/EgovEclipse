package kr.or.ddit.prod.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.prod.service.ProdService;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.validate.utils.ErrorsUtils;
import kr.or.ddit.vo.ProdVO;

//@WebServlet("/prod/prodInsert.do")
@Controller
@RequestMapping("/prod/prodInsert.do")
public class ProdInsertController {
	@Autowired
	private ProdService service;
	private static final String MODELNAME = "prod";
	
	@Autowired
	private ErrorsUtils errorsUtils;
	
	@ModelAttribute(MODELNAME)
	public ProdVO prod() {
		return new ProdVO();
	}
	
	@GetMapping
	public String formUI(){
		return "prod/prodForm";

	}
	@PostMapping
	public String formProcess(
		@Validated (InsertGroup.class) @ModelAttribute(MODELNAME) ProdVO prod
		, BindingResult errors
		, RedirectAttributes redirectAttributes
	){
		String lvn;
		if(!errors.hasErrors()) {
//		3. 검증 통과
//		3-1. 로직을 이용해 등록
			service.createProd(prod);
//		3-2. PRG  패턴을 위한 logical view name 결정
			lvn = "redirect:/prod/prodDetail.do?what="+prod.getProdId();
		}else {
//		4. 검증 실패
//		4-1. 세션 스코프를 이용해서 command object, errors 공유(flash attribute)
			redirectAttributes.addFlashAttribute(MODELNAME, prod);
//			BindErrorUtils.resolve(null)
			MultiValueMap<String, String> customErrors = errorsUtils.errorsToMap(errors);
			redirectAttributes.addFlashAttribute("errors", customErrors);
//			String errorName = BindingResult.MODEL_KEY_PREFIX+MODELNAME;
//			redirectAttributes.addFlashAttribute(errorName, errors);
//		4-2. PRG 패턴을 위한 logical view name 결정
			lvn = "redirect:/prod/prodInsert.do";
		}
		
		return lvn;
	}
}

















