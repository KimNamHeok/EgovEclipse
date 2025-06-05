package kr.or.ddit.case07.controller; // 패키지 선언 (해당 클래스가 속한 패키지 지정)

import org.springframework.stereotype.Controller; // Spring MVC의 컨트롤러 역할을 수행하는 클래스임을 명시
import org.springframework.validation.BindingResult; // 유효성 검증 결과를 처리하는 객체
import org.springframework.web.bind.annotation.GetMapping; // GET 요청을 처리하는 어노테이션
import org.springframework.web.bind.annotation.ModelAttribute; // 모델 속성을 자동으로 바인딩하는 어노테이션
import org.springframework.web.bind.annotation.PostMapping; // POST 요청을 처리하는 어노테이션
import org.springframework.web.bind.annotation.RequestMapping; // 컨트롤러의 기본 요청 URL을 설정하는 어노테이션
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // 리다이렉트 시 데이터를 전달하는 객체

import jakarta.validation.Valid; // 유효성 검증을 위한 어노테이션
import kr.or.ddit.case07.vo.CalculateVO; // VO(Value Object) 클래스 임포트

/** 
 *  피연산자 두 개를 입력받고, 더하기 연산의 결과를 생성하는 컨트롤러
 *  두 피연산자는 양의 정수로 가정
 *  Spring의 객체 검증 모델(BindingResult 사용) 활용
 *  form UI는 form 커스텀 태그 사용
 */
@Controller // 이 클래스가 컨트롤러 역할을 수행한다고 명시
@RequestMapping("/case07/calculate") // "/case07/calculate" 경로로 들어오는 요청을 처리
public class Case07CalculateController {
    static final String MODELNAME = "cal"; // 모델 속성 이름을 정의
    
    @ModelAttribute(MODELNAME) // 모델 속성으로 사용할 객체를 생성
    public CalculateVO calculateVO() {
        return new CalculateVO(); // 새 CalculateVO 객체를 반환
    }
    
    @GetMapping // GET 요청을 처리하는 메서드
    public void formUI() { 
        // 뷰 이름을 반환하지 않으면 요청 경로에 해당하는 뷰가 자동으로 매핑됨
        // 즉, "/case07/calculate" 요청 시 기본 뷰가 렌더링됨
    }
    
    @PostMapping // POST 요청을 처리하는 메서드
    public String formProcess(  
        @Valid @ModelAttribute(MODELNAME) CalculateVO cal, // CalculateVO 객체에 입력 데이터 바인딩 및 유효성 검증 수행
        BindingResult errors, // 검증 결과를 저장하는 객체
        RedirectAttributes redirectAttributes // 리다이렉트 시 데이터를 전달하는 객체
    ) {
        redirectAttributes.addFlashAttribute(MODELNAME, cal); // CalculateVO 객체를 리다이렉트 시 전달
        
        if (errors.hasErrors()) { // 입력값 검증에서 오류가 발생한 경우
            String errorName = BindingResult.MODEL_KEY_PREFIX + MODELNAME; // 오류 정보의 키 생성
            redirectAttributes.addFlashAttribute(errorName, errors); // 오류 정보를 리다이렉트 시 전달
        } else { 
            cal.setResult(cal.getOp1() + cal.getOp2()); // 입력값이 유효한 경우, 두 피연산자의 합을 계산하여 저장
        }
        
        return "redirect:/case07/calculate"; // 입력 폼으로 리다이렉트 (결과를 보기 위해 다시 폼으로 이동)
    }
}