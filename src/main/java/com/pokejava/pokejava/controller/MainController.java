package com.pokejava.pokejava.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pokejava.pokejava.service.JavaQuestionService;
import com.pokejava.pokejava.service.MyPokeService;
import com.pokejava.pokejava.service.PokemonService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private MyPokeService myPokeService;


    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");

        if (memberId == null) {
            return "redirect:/signIn";
        }

        List<PokemonService.PokemonResponse> myPokeList = myPokeService.getMyPokeList(memberId);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMyPokeList = "";
        try {
            jsonMyPokeList = objectMapper.writeValueAsString(myPokeList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        model.addAttribute("myPokeList", jsonMyPokeList);

        return "home";
    }
}