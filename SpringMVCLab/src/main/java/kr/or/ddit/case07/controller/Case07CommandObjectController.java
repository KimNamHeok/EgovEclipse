package kr.or.ddit.case07.controller;

import org.springframework.stereotype.Controller; // Spring MVC의 컨트롤러임을 명시
import org.springframework.ui.Model; // 뷰에 데이터를 전달할 때 사용하는 객체
import org.springframework.validation.BindingResult; // 폼 입력 데이터 검증 결과를 저장하는 객체
import org.springframework.validation.Errors; // 검증 오류 정보를 담는 객체
import org.springframework.validation.annotation.Validated; // 특정 검증 그룹을 지정하여 유효성 검사를 수행하는 어노테이션
import org.springframework.web.bind.annotation.GetMapping; // GET 요청을 처리하는 매핑
import org.springframework.web.bind.annotation.ModelAttribute; // 모델 속성을 자동 생성하는 어노테이션
import org.springframework.web.bind.annotation.RequestMapping; // 요청을 처리하는 핸들러 메소드의 매핑
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // 리다이렉트 시 데이터를 전달하는 객체

import jakarta.validation.Valid; // 폼 데이터를 유효성 검증하는 어노테이션
import kr.or.ddit.case07.vo.DummyVO; // VO 클래스 (폼 데이터 바인딩용 객체)
import kr.or.ddit.validate.InsertGroup; // 특정 검증 그룹을 정의한 클래스
import lombok.extern.slf4j.Slf4j; // 로깅을 위한 Lombok 어노테이션

@Slf4j // 로그를 사용하기 위한 Lombok 어노테이션
@Controller // Spring MVC의 컨트롤러 역할을 수행하는 클래스
@RequestMapping("/case07") // 모든 요청 URL이 "/case07"로 시작하도록 지정
public class Case07CommandObjectController { 

    @ModelAttribute("dummy") // 모델 속성으로 'dummy'를 자동 생성하여 뷰에서 사용 가능하게 함
    public DummyVO dummy() { 
        log.info("dummy 메소드 실행 및 'dummy' 속성 생성"); // 로그 출력 (디버깅 용도)
        return new DummyVO(); // 새로운 DummyVO 객체를 반환하여 폼 초기화
    }
    
    @GetMapping("dummyForm") // "/case07/dummyForm"으로 GET 요청이 오면 이 메소드 실행
    public String formUI() { 
        log.info("dummyForm 핸들러 메소드 실행"); // 로그 출력
        return "case07/form"; // 폼을 표시하는 뷰 파일(case07/form.jsp)로 이동
    }

    // 폼 데이터를 처리하는 메소드
    // 문자로 전달된 name(필수), 숫자로 전달된 age(옵셔널), 문자로 전달된 hobbies(옵셔널)
    @RequestMapping("commandObject02") // "/case07/commandObject02" 요청을 처리
    public String handler02(
        @Validated(InsertGroup.class) @ModelAttribute("dummy") DummyVO dummy, // 유효성 검사를 수행하고 dummy 모델 속성에 바인딩
        BindingResult errors, // 유효성 검증 결과를 저장
        RedirectAttributes redirectAttributes // 리다이렉트 시 데이터를 함께 전달하는 객체
    ) { 
        if(errors.hasErrors()) { // 유효성 검증 실패 시 실행되는 코드
            redirectAttributes.addFlashAttribute("dummy", dummy); // 리다이렉트 시 dummy 객체를 함께 전달
            String errorsName = BindingResult.MODEL_KEY_PREFIX + "dummy"; // 모델에서 오류 속성의 이름을 설정
            redirectAttributes.addFlashAttribute("errors", errors); // 검증 오류를 모델에 추가
            
            log.error("검증 실패, {}", errors); // 검증 실패 로그 출력
            errors.getAllErrors().forEach(oe -> { 
                log.error("{}", oe); // 모든 오류 메시지를 로그로 출력
            });

            return "redirect:/case07/dummyForm"; // 검증 실패 시 폼으로 리다이렉트
        } else { 
            log.info("dummy vo : {}", dummy); // 검증 성공 시 dummy 객체의 내용을 로그로 출력
            return "case07/view01"; // 검증 성공 시 "case07/view01" 뷰로 이동
        }
    }

    @RequestMapping("commandObject01") // "/case07/commandObject01" 요청을 처리하는 핸들러
    public String handler01(@ModelAttribute("dummy") DummyVO dummy, Model model) { 
        // model.addAttribute("dummy", dummy); // Spring이 자동으로 모델에 바인딩하므로 생략 가능
        log.info("dummy vo : {}", dummy); // dummy 객체 정보 로그 출력
        return "case07/view01"; // "case07/view01" 뷰로 이동
    }
}