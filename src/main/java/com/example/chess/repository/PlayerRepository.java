package com.example.chess.repository;

import com.example.chess.entity.Player;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 02.02.2018.
 */
public interface PlayerRepository extends CrudRepository<Player, Long> {
}
