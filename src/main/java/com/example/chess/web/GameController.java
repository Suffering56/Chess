package com.example.chess.web;

import com.example.chess.dto.CellParamsDTO;
import com.example.chess.service.GameService;
import com.example.chess.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 09.01.2018.
 */
@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/start")
    public ResponseEntity<List<List<CellParamsDTO>>> getStartArrangement() {
        List<List<CellParamsDTO>> result = gameService.getStartArrangement();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/moves/{selectedRow}/{selectedColumn}")
    public ResponseEntity<List<List<CellParamsDTO>>> getAvailableMoves(@PathVariable("selectedRow") Integer selectedRow,
                                                                       @PathVariable("selectedColumn") Integer selectedColumn) {
        List<List<CellParamsDTO>> result = gameService.getStartArrangement();

        result.get(rnd()).get(rnd()).available = true;
        result.get(rnd()).get(rnd()).available = true;
        result.get(rnd()).get(rnd()).available = true;
        result.get(selectedRow).get(selectedColumn).selected = true;

        return ResponseEntity.ok(result);
    }

    private static int rnd() {
        return Utils.randInt(0, 7);
    }
}
