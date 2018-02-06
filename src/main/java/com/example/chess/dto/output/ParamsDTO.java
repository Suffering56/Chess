package com.example.chess.dto.output;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 01.02.2018.
 */
@Getter
@Setter
public class ParamsDTO {

    private Long gameId;
    private Integer position = 0;

    private List<List<CellDTO>> cells;
}
