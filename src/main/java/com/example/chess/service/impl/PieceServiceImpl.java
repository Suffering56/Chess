package com.example.chess.service.impl;

import com.example.chess.dto.BoardCellDTO;
import com.example.chess.service.PieceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 19.01.2018.
 */
@Service
public class PieceServiceImpl implements PieceService {

    @Override
    public List<List<BoardCellDTO>> getStartArrangement() {
        List<List<BoardCellDTO>> rows = new ArrayList<>();

        for (int rowIndex = 0; rowIndex < 8; rowIndex++) {
            List<BoardCellDTO> cells = new ArrayList<>();

            for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                BoardCellDTO cell = new BoardCellDTO();

                if (rowIndex == 0 || rowIndex == 1) {
                    cell.side = "white";
                    cell.isEmpty = false;
                } else if (rowIndex == 7 || rowIndex == 6) {
                    cell.side = "black";
                    cell.isEmpty = false;
                }

                if (rowIndex == 1 || rowIndex == 6) {
                    cell.piece = "pawn";
                } else if (rowIndex == 0 || rowIndex == 7) {
                    if (columnIndex == 0 || columnIndex == 7) {
                        cell.piece = "rook";
                    } else if (columnIndex == 1 || columnIndex == 6) {
                        cell.piece = "knight";
                    } else if (columnIndex == 2 || columnIndex == 5) {
                        cell.piece = "bishop";
                    } else if (columnIndex == 3) {
                        cell.piece = "king";
                    } else if (columnIndex == 4) {
                        cell.piece = "queen";
                    }
                }

                cells.add(cell);
            }
            rows.add(cells);
        }

        return rows;
    }
}
