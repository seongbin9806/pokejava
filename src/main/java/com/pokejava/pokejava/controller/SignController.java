package com.pokejava.pokejava.controller;

import com.pokejava.pokejava.service.PokemonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Random;

@Controller
public class SignController {

    @GetMapping("/signIn")
    public String signIn(Model model) {
        return "sign/SignIn";
    }

    @GetMapping("/signUp")
    public String signUp(Model model) {
        return "sign/SignUp";
    }
}
