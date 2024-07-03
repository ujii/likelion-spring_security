package com.example.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 비밀번호 암호화를 위한 빈 생성
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // SecurityFilterChain 커스텀 빈 스프링 빈으로 등록
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // Spring Security 버전에 따라 구현 방식이 다름
        http
                .authorizeHttpRequests(auth -> auth // 람다 식으로 작성 해야함
                        // 메인페이지와 로그인 페이지로의 접근은 모두 허용(인증 필요x)
                        .requestMatchers("/", "/login","/join","/joinProc").permitAll()
                        // admin 페이지로의 접근은 ADMIN 권한을 가진 경우에만 허용
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // my 경로 이후의 모든 경로에 대해서 ADMIN, 혹은 USER권한 보유시 접근 가능
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        // 위에서 설정하지 않은 모든 요청에 대해서는 인증(authenticated)된 사용자에 한해 허용(로그인 필요)
                        .anyRequest().authenticated()
                )
                .formLogin(auth -> auth.loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll())
                // csrf 설정 임시 비활성화
                // 실제 서비스를 위해서는 활성화 시켜주어야한다.
                // 이번 세션은 스프링 시큐리티가 무엇인지 학습하는 것이 목표였기 때문에 개발단계의 편의를 위해 비활성화 하였다.
                .csrf(auth -> auth.disable());

        return http.build();
    }

}