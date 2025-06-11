package kr.or.ddit.security.auth;

import kr.or.ddit.vo.MemberVO;

public interface RealUserWrapper<T> {

	MemberVO getRealUser();
	
}
