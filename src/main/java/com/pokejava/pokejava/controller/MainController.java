package com.pokejava.pokejava.controller;

import com.pokejava.pokejava.service.PokemonService;
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
    public String home(Model model) {
        Random random = new Random();
        int id = random.nextInt(1010) + 1; // 1부터 1010 사이의 랜덤 ID 생성
        PokemonService.PokemonResponse pokemon = pokemonService.fetchPokemonData(id); // 포켓몬 데이터 가져오기
        model.addAttribute("pokemon", pokemon); // 모델에 포켓몬 정보 추가

        return "home"; // "home" 템플릿으로 이동
    }
}