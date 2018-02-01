package com.example.chess.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 01.02.2018.
 */
@Getter
@Setter
public class GameDTO {

    private long gameId;
    private long position = 0;

    private List<List<CellDTO>> cells;
}
