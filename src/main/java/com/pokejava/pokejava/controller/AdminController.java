package com.pokejava.pokejava.controller;

import com.pokejava.pokejava.dto.JavaQuestionForm;
import com.pokejava.pokejava.dto.MemberForm;
import com.pokejava.pokejava.dto.ResponseDTO;
import com.pokejava.pokejava.service.JavaQuestionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {

    @Autowired
    private JavaQuestionService JavaQuestionService;

    @GetMapping("/admin")
    public String adminHome(Model model, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        /* 1은 관리자 */
        if (memberId == null || memberId != 1) {
            // 로그인되지 않은 상태라면 signIn 페이지로 리다이렉트
            return "redirect:/signIn";
        }

        System.out.println(JavaQuestionService.getAllJavaQuestion().getData());
        model.addAttribute("javaQuestionList", JavaQuestionService.getAllJavaQuestion().getData());

        return "admin/home";
    }

    @PostMapping("/admin/getAllJavaQuestion")
    public ResponseEntity<ResponseDTO> getAllJavaQuestion() {
        try {
            ResponseDTO response = JavaQuestionService.getAllJavaQuestion();
            return ResponseEntity.ok(response); // HTTP 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseDTO(false, "정보를 가져오는 중 문제가 발생하였습니다."));
        }
    }

    @PostMapping("/admin/createJavaQuestion")
    public ResponseEntity<ResponseDTO> createJavaQuestion(JavaQuestionForm form) {
        try {
            ResponseDTO response = JavaQuestionService.createJavaQuestion(form);
            return ResponseEntity.ok(response); // HTTP 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseDTO(false, "정보를 가져오는 중 문제가 발생하였습니다."));
        }
    }

    @PostMapping("/admin/deleteJavaQuestion")
    public ResponseEntity<ResponseDTO> deleteJavaQuestion(Long javaQuestionId) {
        try {
            ResponseDTO response = JavaQuestionService.deleteJavaQuestion(javaQuestionId);
            return ResponseEntity.ok(response); // HTTP 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseDTO(false, "정보를 가져오는 중 문제가 발생하였습니다."));
        }
    }
}
