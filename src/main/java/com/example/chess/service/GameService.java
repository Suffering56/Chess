package com.example.chess.service;

import com.example.chess.dto.PointDTO;
import com.example.chess.dto.input.MoveDTO;
import com.example.chess.dto.output.ParamsDTO;
import com.example.chess.entity.Game;
import com.example.chess.exceptions.GameNotMatchedException;

import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 19.01.2018.
 */
public interface GameService {

    ParamsDTO getArrangementByPosition(long gameId, int position);

    List<PointDTO> getAvailableMoves(long gameId, PointDTO selectedCell);

    Game createNewGame();

    ParamsDTO applyMove(long gameId, MoveDTO dto) throws GameNotMatchedException;

    Game findGameById(long gameId);
}
