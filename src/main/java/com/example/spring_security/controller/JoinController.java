package com.example.spring_security.controller;

import com.example.spring_security.dto.JoinDTO;
import com.example.spring_security.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;

    @GetMapping("/join")
    public String joinPage() {
        return "join";
    }

    // 회원가입
    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO) {
        // 회원가입 로직 작성 필요
        joinService.joinProcess(joinDTO); // 회원가입 수행
        return "redirect:/login"; // 회원가입 성공시 로그인 페이지로 연결
    }
}
