package com.khyuna0.khyuna0board;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 스프링부트의 환경설정 파일이라는 것을 명시함
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 annotation
public class SecurityConfig {

	@Bean
	   SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	      http
	         .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
	               .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
	         // h2 DB 접속 콘솔 허가
	         .csrf((csrf) -> csrf 
	        	.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
	         // 스프링 시큐리티에서 로그인 설정
	         .formLogin((formLogin) -> formLogin
	        		 .loginPage("/user/login") // 로그인 요청
	        		 .defaultSuccessUrl("/")) // 로그인 성공 시 이동할 페이지 루트로 지정
	         // 로그아웃 설정
	         .logout((logout) -> logout
	        		 .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 요청 처리
	        		 .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 페이지 지정
	        		 .invalidateHttpSession(true)) // 세션 삭제
	         
	         
	         ; 
	      return http.build();
	   }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean // 스프링 시큐리티에서 인증을 처리하는 매니저 클래스 
	AuthenticationManager authenticationManager (AuthenticationConfiguration
		authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
}
