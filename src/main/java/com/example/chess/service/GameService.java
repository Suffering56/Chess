package com.example.chess.service;

import com.example.chess.dto.output.CellDTO;

import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 19.01.2018.
 */
public interface GameService {

    List<List<CellDTO>> createStartArrangementPieceMatrix();
}
