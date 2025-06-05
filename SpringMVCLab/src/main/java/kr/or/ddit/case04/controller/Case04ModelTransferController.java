package kr.or.ddit.case04.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 	/case04/model01(GET) 요청을 수신하고,
 * 	최종 응답은 "case04/view01" 에서 전송함.
 * 	Model, ModelAndView, @ModelAttribute 사용 방법.
 * 
 * 	*** 최종적으로 HandlerAdapter 에게 Model과 View에 대한 정보를 전달하는 방법. *
 * 	Model : model 은 call by reference 형태로 전달하기 위해 핸들러 메소드의 인자로 Model 을 받음.
 * 			view name 은 반환값의 형태로 전달
 * 	ModelAndView : ModelAndView 라는 반환 객체로 model 과 view 를 한번에 전달.
 * 	@ModelAttribute : 하나의 컨트롤러에 여러 핸들러가 있고, 각 헨들러가 공통적으로 사용할 model 이 있는 경우,
 * 		model 을 반환하는 메소드를 구현하고, 해당 메소드의 반환타입에 @ModelAttribute 를 사용함.
 * 
 */

@Controller
@RequestMapping("/case04")
public class Case04ModelTransferController {
	@GetMapping("model02")
	public ModelAndView handler02(){
		ModelAndView mav = new ModelAndView();
		String modelAttr = "MODEL INFO";
		mav.addObject("modelAttr", modelAttr);
		mav.setViewName("case04/view01");
		return mav;
	}
	
	@GetMapping("model01")
	public String handler01(Model model){
		String modelAttr = "MODEL INFO";
		model.addAttribute("modelAttr", modelAttr);
		return "case04/view01";
	}
}
