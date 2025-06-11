package kr.or.ddit.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import kr.or.ddit.mapper.MemberMapper;
import kr.or.ddit.security.auth.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private MemberMapper mapper;
	
	@Bean
	public CustomUserDetailsService customUserDetailsService() {
		CustomUserDetailsService service = new CustomUserDetailsService(mapper);
		return service;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests(authroize->
				authroize
					.requestMatchers(new AntPathRequestMatcher("/mypage")).authenticated()
					.requestMatchers(new AntPathRequestMatcher("/prod/*Insert*")).hasRole("ADMIN")
					.requestMatchers(new AntPathRequestMatcher("/prod/*Update*")).hasRole("ADMIN")
					.requestMatchers(new AntPathRequestMatcher("/buyer/**")).hasRole("ADMIN")
					.requestMatchers("/**").permitAll()
			)
		.formLogin(login->
			login
				.loginPage("/login")
				.loginProcessingUrl("/login")
				.defaultSuccessUrl("/", false)
		)
		.logout(logout->
			logout
				.logoutUrl("/logout")
		);
		
		
		return http.build();
	}
}












