package com.koreait.rest_24_10.base.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")     // 아래의 모든 설정은 /api/** 경로에만 적용
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/api/*/member/login").permitAll()     // 로그인은 누구나 가능
                                .anyRequest().authenticated()   // 나머지는 인증된 사용자만 가능
                )
                .cors().disable()       // 타 도메인에서 API 호출 가능
                .csrf().disable()       // CSRF 토큰 끄기
                .httpBasic().disable()  // httpBasic 로그인 방식 끄기
                .formLogin().disable()  // 폼 로그인 방식 끄기
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(STATELESS)
                );  // 세션 끄기

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/*
Spring Security 로 하고싶은 것은 ?
- 로그인 할때 이 회원이 누구인지 판별

일반적인 방식
ID/PW를 받아서 -> 유효성 검사 ->  user가 맞다고 객체화해서 http 세션에 저장
-> 세션이 만료되기 전까지 또는 로그아웃 되기 전까지 살아있어야함
-> 매 요청마다 세션키(==쿠키)를 같이 보냄(세션키는 매 요청마다 서버쪽으로 전달된다.)
=> 유저 객체를 로그인 할 당시에 만들어 놓고, 저장해놓고 계속 사용한다.

API
API는 세션이 없는 환경이다. 일반적인 방식처럼 회원 구분을 어떻게 하냐 ?

저장을 못하기 때문에 User/member 객체를 만들지 않음
-> 서버에서 Access Token을 클라이언트에 보냄
-> 클라이언트의 요청을 처리할 때마다  Access Token 이 같이 들어옴
-> 서버는 Access Token이 유효한지 검사 한 뒤 요청을 수용함

user를 만들어서 Spring Security에 등록시킨다.
요청에 대해 응답할 때 등록되어있던 정보가 죽는다.
(유저를 등록해놔도 유지되지는 않는다.)

결론: 액세스 토큰 넘겨줄 때 유저를 만들고 등록을 시켜준다.

 */