package com.pokejava.pokejava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class MyPokemon {
    @Id
    @GeneratedValue
    @Column
    private Long myPokemonId;

    @Column
    private Long userId;

    @Column
    private Long pokemonId;

    @Column
    private LocalDateTime regDate;

    @Column
    private char isUse = 'Y';

    public MyPokemon(Long userId, Long pokemonId) {
        this.userId = userId;
        this.pokemonId = pokemonId;
        this.regDate = LocalDateTime.now(); // regDate는 자동으로 현재 시간으로 설정
    }
}
