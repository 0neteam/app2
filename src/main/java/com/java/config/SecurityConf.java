package com.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 설정 클래스를 나타내는 어노테이션
@EnableWebSecurity //(debug = true) // Spring Security를 활성화하고 디버그 모드를 켬
@EnableMethodSecurity // 메서드 수준의 보안 설정을 활성화
public class SecurityConf {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화를 위한 BCryptPasswordEncoder 빈 등록
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.logout(logout -> {
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")); // GET 요청으로 로그아웃 가능
            logout.invalidateHttpSession(true); // 로그아웃 시 세션 무효화
            logout.clearAuthentication(true); // 인증 정보 삭제
            //logout.logoutSuccessUrl("/"); // 로그아웃 성공 시 리디렉션할 URL 설정
			logout.logoutSuccessHandler((req, res, auth) -> {
				String referer = req.getHeader("Referer");
				String baseurl = referer.substring(0, referer.lastIndexOf("/"));
				res.sendRedirect(baseurl.concat("/"));
			});
            logout.permitAll(); // 모든 사용자에게 로그아웃 허용
        });
        
        http.formLogin(form -> {
            form.loginPage("/signIn"); // 커스텀 로그인 페이지 설정
            form.loginProcessingUrl("/signIn"); // 로그인 처리 URL 설정
            form.usernameParameter("email"); // 로그인 시 입력할 사용자 ID 파라미터명 지정
            form.passwordParameter("pwd"); // 로그인 시 입력할 비밀번호 파라미터명 지정
            form.successHandler((req, res, auth) -> {
                String referer = req.getHeader("Referer");
				String baseurl = referer.substring(0, referer.lastIndexOf("/"));
				res.sendRedirect(baseurl.concat("/"));
            });
            form.failureHandler((req, res, exc) -> {
				String referer = req.getHeader("Referer");
				String baseurl = referer.substring(0, referer.lastIndexOf("/"));
				res.sendRedirect(baseurl.concat("/"));
			});
            form.permitAll(); // 로그인 페이지는 모든 사용자 접근 가능
        });
        
        
        // csrf ignore 설정
        http.cors(cors -> cors.disable());
        http.csrf(csrf -> {
            csrf.ignoringRequestMatchers("/api/**"); // 창고-> 제소사 요청시 
            csrf.ignoringRequestMatchers("/quoState"); // 제소사 발주 상태 요청시 
        });
    
        
        http.authorizeHttpRequests(req -> {
            req.requestMatchers("/", "/signUp").permitAll(); // 메인 페이지 및 회원가입 페이지는 인증 없이 접근 가능
            req.requestMatchers("/css/**", "/js/**", "/img/**").permitAll();
            req.requestMatchers("/webjars/**").permitAll(); // 정적 리소스(webjars) 접근 허용
            
            req.requestMatchers("/user/create/checkemail", "/user/findpw", "/user/loginUpdateAuthCode", "/user/loginUpdateAuthCodeCheck", "/user/loginpwdupdate").permitAll();   
            req.requestMatchers("/biz/create", "/biz/findPwd", "/biz/checkEmail", "/biz/loginUpdateAuthCode", "/biz/loginUpdateAuthCodeCheck", "/biz/loginpwdupdate").permitAll();  
            req.requestMatchers("/quoChk/**", "/quoState").permitAll();
            req.requestMatchers("/api/**").permitAll();
                       
            req.anyRequest().authenticated(); // 그 외 모든 요청은 인증 필요
                        
        });
        
        return http.build(); // 설정을 적용한 SecurityFilterChain 반환
    }
}
