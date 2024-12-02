package com.pokejava.pokejava.service;

import com.pokejava.pokejava.dto.MemberForm;
import com.pokejava.pokejava.dto.MyPokeForm;
import com.pokejava.pokejava.dto.ResponseDTO;
import com.pokejava.pokejava.dto.ResponseListDTO;
import com.pokejava.pokejava.entity.JavaQuestion;
import com.pokejava.pokejava.entity.Member;
import com.pokejava.pokejava.entity.MyPokemon;
import com.pokejava.pokejava.repository.MemberRepository;
import com.pokejava.pokejava.repository.MyPokeRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
public class MyPokeService {
    private final MyPokeRepository myPokeRepository;

    @Autowired
    public MyPokeService(MyPokeRepository myPokeRepository) {
        this.myPokeRepository = myPokeRepository;
    }

    @Autowired
    private PokemonService pokemonService;

    @Transactional
    public List<PokemonService.PokemonResponse> getMyPokeList(Long memberId) {
        List<PokemonService.PokemonResponse> pokemonResponseList = new ArrayList<>();

        try {
            List<MyPokemon> myPokeList = myPokeRepository.findByUserId(memberId);

            for (MyPokemon myPokemon : myPokeList) {
                Long pokemonId = myPokemon.getPokemonId();
                Long myPokemonId = myPokemon.getMyPokemonId();

                PokemonService.PokemonResponse pokemonResponse = pokemonService.fetchPokemonData(pokemonId.intValue());
                pokemonResponse.setPokemonId(pokemonId.intValue());
                pokemonResponse.setMyPokemonId(myPokemonId);

                pokemonResponseList.add(pokemonResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // 예외가 발생하면 빈 리스트 반환
        }

        return pokemonResponseList;
    }

    @Transactional
    public ResponseDTO catchMyPokemon(MyPokeForm form) {
        try{
            MyPokemon poke = form.toEntity(); // DTO -> Entity 변환
            MyPokemon savedPoke = myPokeRepository.save(poke);
            Long myPokemonId = savedPoke.getMyPokemonId();

            return new ResponseDTO(true, "포켓몬 잡기 성공", myPokemonId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(false, "포켓몬 잡기 실패");
        }
    }

    @Transactional
    public ResponseDTO putMyPokemon(Long myPokemonId) {
        try{
            myPokeRepository.deleteById(myPokemonId);

            return new ResponseDTO(true, "포켓몬 놓아주기 성공");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDTO(false, "포켓몬 놓아주기 실패");
        }
    }
}
