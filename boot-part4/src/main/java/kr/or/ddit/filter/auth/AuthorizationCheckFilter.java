package kr.or.ddit.filter.auth;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.util.AntPathMatcher;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorizationCheckFilter extends HttpFilter {
	private final Map<String, List<String>> securedResources;
	
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 보호자원에 대한 요청인지 판단
		String uri = request.getRequestURI();
		AntPathMatcher matcher = new AntPathMatcher();
		boolean secured = false;
		List<String> allowedRoles = null;
		for(String key : securedResources.keySet()) {
			secured = matcher.match(key, uri);
			if(secured) {
			    allowedRoles = securedResources.get(key);
				break;
			}
		}
		boolean grandedUser = true;
		boolean pass = true;
		if(secured) {
			// 보호자원 요청
			// 권한 획득 여부 판단
			MemberVOWrapper principal = (MemberVOWrapper) request.getUserPrincipal();
			String userRole = principal.getRealUser().getMemRole();
			// 권한 소유 여부 : 자원에 설정된 접근 권한과 사용자가 부여받은 역할의 일치 여부
			grandedUser = allowedRoles.contains(userRole);
			if(grandedUser) {
				// 인가된 사용자(권한있음) , 통과
				pass = true;
			}else {
				// 비인가 사용자(권한없음) , 403 에러 전송(권한 없으니 들어오면 안된다)
				pass = false;
			}
		}else {
			// 비보호자원 요청 , 통과
			pass = true;
		}
		if(pass) {
			chain.doFilter(request, response);
		}else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "접근 권한이 없음.");
		}
	}
}






