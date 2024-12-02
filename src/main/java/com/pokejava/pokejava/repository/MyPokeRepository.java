package com.pokejava.pokejava.repository;

import com.pokejava.pokejava.entity.MyPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MyPokeRepository extends JpaRepository<MyPokemon, Long> {
    List<MyPokemon> findByUserId(Long memberId);
}