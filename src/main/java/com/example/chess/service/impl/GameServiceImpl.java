package com.example.chess.service.impl;

import com.example.chess.dto.PointDTO;
import com.example.chess.dto.output.CellDTO;
import com.example.chess.enums.PieceType;
import com.example.chess.enums.Side;
import com.example.chess.service.GameService;
import com.example.chess.util.Utils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 19.01.2018.
 */
@Service
public class GameServiceImpl implements GameService {

    private static final int BOARD_SIZE = 8;

    @Override
    public List<List<CellDTO>> createStartArrangementPieceMatrix() {
        List<List<CellDTO>> rows = new ArrayList<>();

        //1-8
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            List<CellDTO> cells = new ArrayList<>();

            //A-H
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                CellDTO cell = new CellDTO(rowIndex, columnIndex);

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
                        cell.setPiece(PieceType.king);
                    } else {  //columnIndex == 4
                        cell.setPiece(PieceType.queen);
                    }
                }

                cells.add(cell);
            }
            rows.add(cells);
        }

        return rows;
    }

    @Override
    public List<PointDTO> getAvailableMoves(long gameId, PointDTO selectedCell) {
        return new ArrayList<PointDTO>() {{
            add(new PointDTO(3, 3));
            add(new PointDTO(4, 3));
            add(new PointDTO(rnd(), rnd()));
        }};
    }

    private static int rnd() {
        return Utils.generateRandomInt(4, 7);
    }
}
