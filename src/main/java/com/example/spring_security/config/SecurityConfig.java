package com.example.spring_security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // SecurityFilterChain 커스텀 빈 스프링 빈으로 등록
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        // Spring Security 버전에 따라 구현 방식이 다름
        http
                .authorizeHttpRequests(auth -> auth // 람다 식으로 작성 해야함
                        // 메인페이지와 로그인 페이지로의 접근은 모두 허용(인증 필요x)
                        .requestMatchers("/", "/login").permitAll()
                        // admin 페이지로의 접근은 ADMIN 권한을 가진 경우에만 허용
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // my 경로 이후의 모든 경로에 대해서 ADMIN, 혹은 USER권한 보유시 접근 가능
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")
                        // 위에서 설정하지 않은 모든 요청에 대해서는 인증(authenticated)된 사용자에 한해 허용(로그인 필요)
                        .anyRequest().authenticated()
                );

        return http.build();
    }

}
