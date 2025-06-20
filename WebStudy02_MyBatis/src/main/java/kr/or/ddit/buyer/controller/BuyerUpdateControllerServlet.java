package kr.or.ddit.buyer.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.or.ddit.ViewResolverComposite;
import kr.or.ddit.buyer.service.BuyerService;
import kr.or.ddit.buyer.service.BuyerServiceImpl;
import kr.or.ddit.validate.UpdateGroup;
import kr.or.ddit.validate.util.ValidateUtils;
import kr.or.ddit.vo.BuyerVO;

/**
 *  /buyer/buyerUpdate.do (GET, POST)
 */
@WebServlet("/buyer/buyerUpdate.do")
public class BuyerUpdateControllerServlet extends HttpServlet{
	private BuyerService service = new BuyerServiceImpl();
	/**
	 * 등록 form 제공
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		BuyerVO buyer = (BuyerVO) session.getAttribute("buyer");
		session.removeAttribute("buyer"); // flash attribute 방식
		Map<String, String> errors = (Map<String, String>) session.getAttribute("errors");
		session.removeAttribute("errors");
		
		req.setAttribute("buyer", buyer);
		req.setAttribute("errors", errors);
		
		String buyerId = req.getParameter("what");
		if(StringUtils.isBlank(buyerId)) {
			resp.sendError(400, "필수 파라미터 누락");
			return;
		}
		if(buyer==null) {
			buyer = service.readBuyer(buyerId).get();
			req.setAttribute("buyer", buyer);
		}
		
		String lvn = "buyer/buyerEdit";
		new ViewResolverComposite().resolveView(lvn, req, resp);
	}
	
	/**
	 *
	 * form 으로 입력받은 데이터 처리
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		String what = req.getParameter("what");
		BuyerVO buyer = new BuyerVO();
//		buyer.setBuyerName(req.getParameter("buyerName"));
		try {
			BeanUtils.populate(buyer, req.getParameterMap());
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new ServletException(e);
		}
//		BuyerVO (command object) 가 검증 대상
//		Map<String, String> errors = new HashMap<>();
//		boolean valid = validate(buyer, errors);
		Map<String, String> errors = 
				ValidateUtils.validate(buyer, UpdateGroup.class);
		String lvn;
		if(errors.isEmpty()) {
			service.modifyBuyer(buyer);
			// PRG 패턴
			
			lvn = "redirect:/buyer/buyerDetail.do?what="+buyer.getBuyerId();
		}else {
			HttpSession session = req.getSession();
			session.setAttribute("buyer", buyer);
			session.setAttribute("errors", errors);
//			/buyer/buyerInsert.do (redirect--GET)
			lvn= "redirect:/buyer/buyerUpdate.do?what="+what;
		}
		new ViewResolverComposite().resolveView(lvn, req, resp);
	}

	private boolean validate(BuyerVO buyer, Map<String, String> errors) {
		boolean valid = true;
		if(StringUtils.isBlank(buyer.getBuyerId())) {
			valid=false;
			errors.put("buyerId", "제조사 코드 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyerName())) {
			valid=false;
			errors.put("buyerName", "제조사 이름 누락");
		}
		if(StringUtils.isBlank(buyer.getLprodGu())) {
			valid=false;
			errors.put("lprodGu", "제조사 분류 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyerBank())) {
			valid=false;
			errors.put("buyerBank", "거래 은행 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyerBankno())) {
			valid=false;
			errors.put("buyerBankno", "계좌 번호 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyerBankname())) {
			valid=false;
			errors.put("buyerBankname", "계좌주 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyerComtel())) {
			valid=false;
			errors.put("buyerComtel", "연락처 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyerMail())) {
			valid=false;
			errors.put("buyerMail", "메일 누락");
		}
		if(StringUtils.isBlank(buyer.getBuyerCharger())) {
			valid=false;
			errors.put("buyerCharger", "담당자 누락");
		}
		return valid;
	}
}















