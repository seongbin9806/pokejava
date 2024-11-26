package com.pokejava.pokejava.controller;

import com.pokejava.pokejava.dto.ResponseDTO;
import com.pokejava.pokejava.dto.MemberForm;
import com.pokejava.pokejava.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class SignController {

    private final MemberService memberService;

    @Autowired
    public SignController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/signIn")
    public String signIn(Model model, HttpSession session) {
        // 이미 로그인된 상태라면 메인 페이지로 리다이렉트
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId != null) {
            return "redirect:/";  // 메인 페이지로 리다이렉트
        }

        return "sign/SignIn";
    }

    @PostMapping("/sign/signIn")
    public ResponseEntity<ResponseDTO> goLogin(MemberForm form, HttpSession session) {
        ResponseDTO response = memberService.signIn(form);


        // ResponseEntity를 사용하여 상태 코드와 함께 반환
        if (response.isResult()) {
            Long memberId = memberService.getMemberIdByEmail(form.getMemberEmail());
            session.setAttribute("memberId", memberId);

            return ResponseEntity.ok(response); // HTTP 200 OK
        } else {
            return ResponseEntity.status(403).body(response); // HTTP 500 Internal Server Error
        }
    }

    @GetMapping("/signUp")
    public String signUp(Model model, HttpSession session) {
        // 이미 로그인된 상태라면 메인 페이지로 리다이렉트
        Long memberId = (Long) session.getAttribute("memberId");
        if (memberId != null) {
            return "redirect:/";  // 메인 페이지로 리다이렉트
        }

        return "sign/SignUp";
    }

    @PostMapping("/sign/signUp")
    public ResponseEntity<ResponseDTO> goSignUp(MemberForm form, HttpSession session) {
        ResponseDTO response = memberService.signUp(form);

        // ResponseEntity를 사용하여 상태 코드와 함께 반환
        if (response.isResult()) {
            Long memberId = memberService.getMemberIdByEmail(form.getMemberEmail());
            session.setAttribute("memberId", memberId);

            return ResponseEntity.ok(response); // HTTP 200 OK
        } else {
            return ResponseEntity.status(403).body(new ResponseDTO(false, "이미 존재하는 이메일입니다.")); // HTTP 500 Internal Server Error
        }
    }

    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 세션 무효화
        return "redirect:/signIn";  // 로그인 페이지로 리다이렉트
    }
}
