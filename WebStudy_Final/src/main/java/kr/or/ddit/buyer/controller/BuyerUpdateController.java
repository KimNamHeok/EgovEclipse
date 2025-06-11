package kr.or.ddit.buyer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.ddit.buyer.service.BuyerService;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.utils.ErrorsUtils;
import kr.or.ddit.vo.BuyerVO;
import lombok.RequiredArgsConstructor;

/**
 *  /buyer/buyerUpdate.do (GET, POST)
 */
@Controller
@RequestMapping("/buyer/buyerUpdate.do")
@RequiredArgsConstructor
public class BuyerUpdateController{
	private final BuyerService service;
	
	private ErrorsUtils errorUtils;
	@Autowired(required = false)
	public void setErrorUtils(ErrorsUtils errorUtils) {
		this.errorUtils = errorUtils;
	}
	
	static final String MODELNAME = "buyer";
	/**
	 * 등록 form 제공
	 */
	@GetMapping
	public String editForm(
		String what
		, Model model
	) {
		if(!model.containsAttribute(MODELNAME)) {
			BuyerVO buyer = service.readBuyer(what).get();
			model.addAttribute(MODELNAME, buyer);
		}
		
	
		return "buyer/buyerEdit";
	}
	
	/**
	 *
	 * form 으로 입력받은 데이터 처리
	 */
	@PostMapping
	public String formProcess(
		String what
		, @Validated(UpdateGroup.class) @ModelAttribute(MODELNAME) BuyerVO buyer
		, BindingResult errors
		, RedirectAttributes redirectAttributes
	) {
		
		String lvn;
		if(errors.hasErrors()) {
			service.modifyBuyer(buyer);
			// PRG 패턴
			
			lvn = "redirect:/buyer/buyerDetail.do?what="+buyer.getBuyerId();
		}else {
			redirectAttributes.addFlashAttribute(MODELNAME, buyer);
			MultiValueMap<String, String> customErrors = errorUtils.errorsToMap(errors);
			redirectAttributes.addFlashAttribute("errors", errors);
//			/buyer/buyerInsert.do (redirect--GET)
			lvn= "redirect:/buyer/buyerUpdate.do?what="+what;
		}
		return lvn;
	}


}















