package com.pokejava.pokejava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
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
}
