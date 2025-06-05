package kr.or.ddit.buyer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import kr.or.ddit.buyer.service.BuyerService;
import kr.or.ddit.case07.vo.CalculateVO;
import kr.or.ddit.validate.InsertGroup;
import kr.or.ddit.vo.BuyerVO;

/**
 *  /buyer/buyerInsert.do (GET, POST)
 */
@Controller
@RequestMapping("/buyer/buyerInsert.do")
public class BuyerInsertController{
	@Autowired
	private BuyerService service;
	
	static final String MODELNAME = "buyer";
	
	@ModelAttribute(MODELNAME)
	public BuyerVO buyer() {
		return new BuyerVO();
	}
	
	/**
	 * 등록 form 제공
	 */
	@GetMapping
	protected String formUI() {
		
		return "buyer/buyerForm";
	}
	
	@PostMapping
	public String formProcess(
		@Validated(InsertGroup.class) @ModelAttribute(MODELNAME) BuyerVO buyer
		, BindingResult errors
		, RedirectAttributes redirectAttributes
	) {
		String lvn;
		if(errors.hasErrors()) {
			service.createBuyer(buyer);
//			// PRG 패턴
			lvn = "redirect:/buyer/buyerList.do";
		}else {
			redirectAttributes.addFlashAttribute(MODELNAME, buyer);
			String errorName = BindingResult.MODEL_KEY_PREFIX + MODELNAME;
			redirectAttributes.addFlashAttribute(errorName, errors);
//			/buyer/buyerInsert.do (redirect--GET)
			lvn = "/buyer/buyerInsert.do";
		}
		return lvn;
	}
}
	
	/**
	 *
	 * form 으로 입력받은 데이터 처리
	 */
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
//	throws ServletException, IOException {
//		BuyerVO buyer = new BuyerVO();
////		buyer.setBuyerName(req.getParameter("buyerName"));
//		try {
//			BeanUtils.populate(buyer, req.getParameterMap());
//		} catch (IllegalAccessException | InvocationTargetException e) {
//			throw new ServletException(e);
//		}
////		BuyerVO (command object) 가 검증 대상
////		Map<String, String> errors = new HashMap<>();
////		boolean valid = validate(buyer, errors);
//		
//		Map<String, String> errors = 
//				ValidateUtils.validate(buyer, InsertGroup.class);
//		
//		String lvn;
//		if(errors.isEmpty()) {
//			service.createBuyer(buyer);
//			// PRG 패턴
//			lvn = "redirect:/buyer/buyerList.do";
//		}else {
//			HttpSession session = req.getSession();
//			session.setAttribute("buyer", buyer);
//			session.setAttribute("errors", errors);
////			/buyer/buyerInsert.do (redirect--GET)
//			lvn = "redirect:/buyer/buyerInsert.do";
//		}
//		new ViewResolverComposite().resolveView(lvn, req, resp);
//	}


















