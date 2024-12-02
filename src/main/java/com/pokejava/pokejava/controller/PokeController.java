package com.pokejava.pokejava.controller;

import com.pokejava.pokejava.dto.MyPokeForm;
import com.pokejava.pokejava.dto.ResponseDTO;
import com.pokejava.pokejava.dto.MemberForm;
import com.pokejava.pokejava.dto.ResponseListDTO;
import com.pokejava.pokejava.service.JavaQuestionService;
import com.pokejava.pokejava.service.MemberService;
import com.pokejava.pokejava.service.MyPokeService;
import com.pokejava.pokejava.service.PokemonService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Random;

@Controller
@Slf4j
public class PokeController {

    @Autowired
    private PokemonService pokemonService;

    @Autowired
    private MyPokeService myPokeService;

    @Autowired
    private JavaQuestionService javaQuestionService;

    @PostMapping("/poke/getRandomPoke")
    public ResponseEntity<ResponseDTO> getRandomPoke() {
        try {
            Random random = new Random();
            int id = random.nextInt(1010) + 1; // 1부터 1010 사이의 랜덤 ID 생성
            PokemonService.PokemonResponse pokemon = pokemonService.fetchPokemonData(id); // 포켓몬 데이터 가져오기
            pokemon.setPokemonId(id);

            return ResponseEntity.ok(new ResponseDTO(true, "포켓몬 정보 가져오기 성공", pokemon));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseDTO(false, "정보를 가져오는 중 문제가 발생하였습니다."));
        }
    }

    @PostMapping("/poke/getJavaQuestion")
    public ResponseEntity<ResponseDTO> getJavaQuestion() {
        try {
            ResponseDTO response = javaQuestionService.getRandomJavaQuestion();
            return ResponseEntity.ok(response); // HTTP 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseDTO(false, "정보를 가져오는 중 문제가 발생하였습니다."));
        }
    }

    @PostMapping("/poke/catchPokemon")
    public ResponseEntity<ResponseDTO> catchPokemon(Long pokemonId, HttpSession session) {
        try {
            Long memberId = (Long) session.getAttribute("memberId");
            MyPokeForm myPokeForm = new MyPokeForm(memberId, pokemonId);

            ResponseDTO response = myPokeService.catchMyPokemon(myPokeForm);
            return ResponseEntity.ok(response); // HTTP 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseDTO(false, "정보를 가져오는 중 문제가 발생하였습니다."));
        }
    }

    @PostMapping("/poke/putMyPokemon")
    public ResponseEntity<ResponseDTO> putMyPokemon(Long myPokemonId) {
        try {
            ResponseDTO response = myPokeService.putMyPokemon(myPokemonId);
            return ResponseEntity.ok(response); // HTTP 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ResponseDTO(false, "정보를 가져오는 중 문제가 발생하였습니다."));
        }
    }
}
