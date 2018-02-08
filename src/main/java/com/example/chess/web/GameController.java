package com.example.chess.web;

import com.example.chess.dto.PointDTO;
import com.example.chess.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 07.02.2018.
 */
@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/{gameId}/move")
    public List<PointDTO> getAvailableMoves(@PathVariable("gameId") long gameId,
                                            @RequestBody PointDTO dto) {

        return gameService.getAvailableMoves(gameId, dto);
    }
}
