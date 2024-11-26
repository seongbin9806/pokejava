package com.pokejava.pokejava.dto;

import com.pokejava.pokejava.entity.Member;
import com.pokejava.pokejava.entity.MyPokemon;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MyPokeForm {

    private Long userId;
    private Long pokemonId;

    public MyPokemon toEntity() {
        return new MyPokemon(userId, pokemonId);
    }
}
