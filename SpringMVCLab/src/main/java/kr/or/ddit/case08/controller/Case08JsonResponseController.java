package kr.or.ddit.case08.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import kr.or.ddit.case03.view.GsonView;

@Controller
@RequestMapping("/case08")
public class Case08JsonResponseController {
	
	/**
	 * @param model
	 *  accept 요청 헤더를 기준으로 content-type 을 협상하는 방식.
	 *  {@link ContentNegotiatingViewResolver} 의 동장 방식에 따라
	 *  {@link GsonView}에서 Model 을 대상으로 마샬링이 처리되는 방식
	 */
	@GetMapping("json02")
	public void handler02(Model model) {
		Map<String, Object> original = new HashMap<>();
		original.put("prop1", "문자열");
		original.put("prop2", 23);
		original.put("prop3", new String[] {"a1", "b2"});
		model.addAllAttributes(original);
	}
	
	/**
	 * accept 헤더와 무관하게 마샬링이 처리됨.
	 * HandlerAdapter 에 의해 response entity 가 처리되는 형태.
	 *  마샬링의 대상이 되는 response entity 는
	 *  헨들러 메소드의 리턴타입에 @ResponseBody 로 표현함.
	 * @return
	 */
	@GetMapping("json01")
	@ResponseBody
	public Map<String, Object> handler01() {
		Map<String, Object> original = new HashMap<>();
		original.put("prop1", "문자열");
		original.put("prop2", 23);
		original.put("prop3", new String[] {"a1", "b2"});
		return original;
	}
}
