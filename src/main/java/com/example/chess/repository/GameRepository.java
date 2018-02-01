package com.example.chess.repository;

import com.example.chess.entity.Game;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 01.02.2018.
 */
public interface GameRepository extends CrudRepository<Game, Long> {
}
