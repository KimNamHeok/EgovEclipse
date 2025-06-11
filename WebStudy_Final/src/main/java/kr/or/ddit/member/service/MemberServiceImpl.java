package kr.or.ddit.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.ddit.common.exception.PKDuplicatedException;
import kr.or.ddit.mapper.MemberMapper;
import kr.or.ddit.vo.MemberVO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberMapper mapper;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public void createMember(MemberVO member) {
		if(mapper.selectMember(member.getMemId())==null){
			String encoded = passwordEncoder.encode(member.getMemPassword());
			member.setMemPassword(encoded);
			mapper.insertMember(member);
		}else {
			throw new PKDuplicatedException(member.getMemId());
		}
	}
	
	@Override
	public MemberVO readMember(String username) {
		return mapper.selectMember(username);
	}

}
