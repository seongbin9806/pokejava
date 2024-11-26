package com.pokejava.pokejava.controller;

import com.pokejava.pokejava.service.PokemonService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class MainController {

    @Autowired
    private PokemonService pokemonService;

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        if (memberId == null) {
            // 로그인되지 않은 상태라면 signIn 페이지로 리다이렉트
            return "redirect:/signIn";
        }

        return "home"; // "home" 템플릿으로 이동
    }
}