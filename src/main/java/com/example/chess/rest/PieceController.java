package com.example.chess.rest;

import com.example.chess.dto.CellParamsDTO;
import com.example.chess.service.PieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 09.01.2018.
 */
@RestController
@RequestMapping("/api/piece")
public class PieceController {

    private final PieceService pieceService;

    @Autowired
    public PieceController(PieceService pieceService) {
        this.pieceService = pieceService;
    }

    @GetMapping("/arrangement/start")
    public ResponseEntity<List<List<CellParamsDTO>>> getStartArrangement() {
        List<List<CellParamsDTO>> result = pieceService.getStartArrangement();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/moves/{selectedRow}/{selectedColumn}")
    public ResponseEntity<List<List<CellParamsDTO>>> getAvailableMoves(@PathVariable("selectedRow") Integer selectedRow,
                                                                       @PathVariable("selectedColumn") Integer selectedColumn) {
        List<List<CellParamsDTO>> result = pieceService.getStartArrangement();

        result.get(rnd()).get(rnd()).available = true;
        result.get(rnd()).get(rnd()).available = true;
        result.get(rnd()).get(rnd()).available = true;
        result.get(selectedRow).get(selectedColumn).selected = true;

        return ResponseEntity.ok(result);
    }

    private static int rnd() {
        return randInt(0, 7);
    }

    private static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
