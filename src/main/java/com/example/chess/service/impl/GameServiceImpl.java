package com.example.chess.service.impl;

import com.example.chess.dto.CellDTO;
import com.example.chess.enums.PieceType;
import com.example.chess.enums.Side;
import com.example.chess.service.GameService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 19.01.2018.
 */
@Service
public class GameServiceImpl implements GameService {



    @Override
    public List<List<CellDTO>> getStartArrangement() {
        List<List<CellDTO>> rows = new ArrayList<>();

        for (int rowIndex = 7; rowIndex >= 0; rowIndex--) {
            List<CellDTO> cells = new ArrayList<>();

            for (int columnIndex = 0; columnIndex < 8; columnIndex++) {
                CellDTO cell = new CellDTO();

                if (rowIndex == 0 || rowIndex == 1) {
                    cell.setSide(Side.white);
                } else if (rowIndex == 7 || rowIndex == 6) {
                    cell.setSide(Side.black);
                }

                if (rowIndex == 1 || rowIndex == 6) {
                    cell.setPiece(PieceType.pawn);
                } else if (rowIndex == 0 || rowIndex == 7) {
                    if (columnIndex == 0 || columnIndex == 7) {
                        cell.setPiece(PieceType.rook);
                    } else if (columnIndex == 1 || columnIndex == 6) {
                        cell.setPiece(PieceType.knight);
                    } else if (columnIndex == 2 || columnIndex == 5) {
                        cell.setPiece(PieceType.bishop);
                    } else if (columnIndex == 3) {
                        cell.setPiece(PieceType.queen);
                    } else {  //columnIndex == 4
                        cell.setPiece(PieceType.king);
                    }
                }

                cells.add(cell);
            }
            rows.add(cells);
        }

        return rows;
    }
}
