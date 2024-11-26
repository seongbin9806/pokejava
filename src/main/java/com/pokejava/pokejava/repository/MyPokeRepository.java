package com.pokejava.pokejava.repository;

import com.pokejava.pokejava.entity.MyPokemon;
import org.springframework.data.repository.CrudRepository;

public interface MyPokeRepository extends CrudRepository<MyPokemon, Long> {

}