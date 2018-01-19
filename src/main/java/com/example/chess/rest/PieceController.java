package com.example.chess.rest;

import com.example.chess.dto.BoardCellDTO;
import com.example.chess.service.PieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<List<BoardCellDTO>>> getStartArrangement() {
        List<List<BoardCellDTO>> result = pieceService.getStartArrangement();

        return ResponseEntity.ok(result);
    }
}
