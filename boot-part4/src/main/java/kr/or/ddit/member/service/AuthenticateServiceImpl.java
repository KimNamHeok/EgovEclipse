package kr.or.ddit.member.service;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.MemberMapper;
import kr.or.ddit.vo.MemberVO;
import lombok.RequiredArgsConstructor;

/**
 * Layered Architecture : 하나의 명령이 처리되는 동안 순차적으로 객체들이 동작하는 구조
 * 		계층을 구성하고 있는 객체들간의 (의존)관계를 기반으로 한 설계 구조
 */
@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {
	
	private final MemberMapper dao;  
	private final PasswordEncoder pe;
	
	
	// authenticate 메서드의 역할은 단순히 비밀번호를 검사하고 결과(T or F)를 반환하는 것
	@Override
	public boolean authenticate(MemberVO inputData) {	// inputData라는 MemberVO 타입의 객체는 클라이언트나 상위 계층으로부터 넘어온 회원정보를 담고 있음
		MemberVO saved = dao.selectMember(inputData.getMemId());	// 해당 아이디에 해당하는 저장된 회원 데이터를 조회하고, saved 객체에 저장 / 만약 해당 아이디가 없는 경우, saved는 null
		if(saved!=null) {			// 조건문을 통해 조회된 회원 데이터가 존재하는지를 검사
			String inputPass = inputData.getMemPassword();	// 우리가 입력한 패스워드
			String savedPass = saved.getMemPassword();		// DB에 저장된 패스워드
			boolean result = pe.matches(inputPass, savedPass);	// 위에 둘 비교 같으면 T 틀리면 F
			if(result) {
//				inputData.setMemName(saved.getMemName());
//				inputData.setMemMail(saved.getMemMail());
//				inputData.setMemBir(saved.getMemBir());
				BeanUtils.copyProperties(saved, inputData);	  // saved 객체의 모든 프로퍼티를 inputData 객체에 복사
			}
			return result;
		}else {
			return false;
		}
	}

}
