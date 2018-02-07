package com.example.chess.web;

import com.example.chess.dto.PointDTO;
import com.example.chess.dto.output.CellDTO;
import com.example.chess.dto.output.ParamsDTO;
import com.example.chess.service.GameService;
import com.example.chess.util.Utils;
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
    public ParamsDTO getAvailableMoves(@PathVariable("gameId") Long gameId,
                                       @RequestBody PointDTO dto) {
        List<List<CellDTO>> cells = gameService.createStartArrangementPieceMatrix();

        cells.get(3).get(3).setAvailable(true);
        cells.get(4).get(3).setAvailable(true);
        cells.get(rnd()).get(rnd()).setAvailable(true);
        cells.get(dto.getRowIndex()).get(dto.getColumnIndex()).setSelected(true);

        ParamsDTO result = new ParamsDTO();
        result.setGameId(gameId);
        result.setCells(cells);

        return result;
    }


//    @PostMapping("/{gameId}/move")
//    public List<PointDTO> xgetAvailableMoves(@PathVariable("gameId") Long gameId,
//                                             @RequestBody PointDTO dto) {
//        List<List<CellDTO>> cells = gameService.createStartArrangementPieceMatrix();
//
//        cells.get(3).get(3).setAvailable(true);
//        cells.get(4).get(3).setAvailable(true);
//        cells.get(rnd()).get(rnd()).setAvailable(true);
//        cells.get(dto.getRowIndex()).get(dto.getColumnIndex()).setSelected(true);
//
//        ParamsDTO result = new ParamsDTO();
//        result.setGameId(gameId);
//        result.setCells(cells);
//
//        return null;
//    }

    private static int rnd() {
        return Utils.generateRandomInt(4, 7);
    }
}
