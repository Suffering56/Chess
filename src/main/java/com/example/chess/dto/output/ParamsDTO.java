package com.example.chess.dto.output;

import com.example.chess.entity.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 01.02.2018.
 */
@Getter
@Setter
public class ParamsDTO {

    private List<List<CellDTO>> piecesMatrix;
    private Game game;
}
