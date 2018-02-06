package com.example.chess.repository;

import com.example.chess.entity.History;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 06.02.2018.
 */
public interface HistoryRepository extends CrudRepository<History, Long> {

}
