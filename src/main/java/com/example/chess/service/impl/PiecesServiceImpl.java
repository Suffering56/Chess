package com.example.chess.service.impl;

import com.example.chess.dto.PointDTO;
import com.example.chess.dto.output.CellDTO;
import com.example.chess.enums.Side;
import com.example.chess.service.PieceService;
import com.example.chess.util.Utils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PiecesServiceImpl implements PieceService {

    private List<List<CellDTO>> piecesMatrix;
    private Side alliedSide;
    private Side enemySide;

    @Override
    public List<PointDTO> getAvailableMoves(CellDTO selectedCell) {
        alliedSide = selectedCell.getSide();
        enemySide = getEnemySide(selectedCell);

        switch (selectedCell.getPiece()) {
            case pawn: {
                return getMovesForPawn(selectedCell);
            }
            case knight: {
                return getMovesForKnight(selectedCell);
            }
            case bishop: {
                return getMovesForBishop(selectedCell);
            }
            case rook: {
                return getMovesForRook(selectedCell);
            }
            case queen: {
                return getMovesForQueen(selectedCell);
            }
            case king: {
                return getMovesForKing(selectedCell);
            }
        }

        return new ArrayList<>();
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    private List<PointDTO> getMovesForPawn(CellDTO selectedCell) {
        List<PointDTO> result = new ArrayList<>();

        int vector = 1;
        if (selectedCell.getSide() == Side.black) {
            vector = -1;
        }

        boolean isFirstMove = false;
        if (selectedCell.getRowIndex() == 1 || selectedCell.getRowIndex() == 6) {
            isFirstMove = true;
        }

        CellDTO cell = getSelectedCell(selectedCell.getRowIndex() + 1 * vector, selectedCell.getColumnIndex());
        if (cell.getPiece() == null) {
            result.add(cell);
        }

        if (isFirstMove) {
            cell = getSelectedCell(selectedCell.getRowIndex() + 2 * vector, selectedCell.getColumnIndex());
            if (cell.getPiece() == null) {
                result.add(cell);
            }
        }

        //attack
        cell = getSelectedCell(selectedCell.getRowIndex() + vector, selectedCell.getColumnIndex() + 1);
        if (cell != null && cell.getPiece() != null && cell.getSide() == enemySide) {
            result.add(cell);
        }
        cell = getSelectedCell(selectedCell.getRowIndex() + vector, selectedCell.getColumnIndex() - 1);
        if (cell != null && cell.getPiece() != null && cell.getSide() == enemySide) {
            result.add(cell);
        }

        //TODO: реализовать взятие на проходе

        return result;
    }

    private List<PointDTO> getMovesForKnight(CellDTO selectedCell) {
        List<PointDTO> result = new ArrayList<>();

        return result;
    }

    private List<PointDTO> getMovesForBishop(CellDTO selectedCell) {
        List<PointDTO> result = new ArrayList<>();


        return result;
    }

    private boolean addAvailableRookMove(List<PointDTO> movesList, CellDTO cell) {
        if (cell.getSide() == alliedSide) {
            return true;
        }

        movesList.add(cell);

        if (cell.getSide() == enemySide) {
            return true;
        }
        return false;
    }

    private List<PointDTO> getMovesForRook(CellDTO selectedCell) {
        List<PointDTO> result = new ArrayList<>();

        CellDTO cell;
        int selectedRow = selectedCell.getRowIndex();
        int selectedColumn = selectedCell.getColumnIndex();

        //vertical
        for (int rowIndex = selectedRow - 1; rowIndex >= 0; rowIndex--) {
            cell = getSelectedCell(rowIndex, selectedColumn);
            if (addAvailableRookMove(result, cell)) {
                break;
            }
        }
        for (int rowIndex = selectedRow + 1; rowIndex < 8; rowIndex++) {
            cell = getSelectedCell(rowIndex, selectedColumn);
            if (addAvailableRookMove(result, cell)) {
                break;
            }
        }

        //horizontal
        for (int columnIndex = selectedColumn - 1; columnIndex >= 0; columnIndex--) {
            cell = getSelectedCell(selectedRow, columnIndex);
            if (addAvailableRookMove(result, cell)) {
                break;
            }
        }
        for (int columnIndex = selectedColumn + 1; columnIndex < 8; columnIndex++) {
            cell = getSelectedCell(selectedRow, columnIndex);
            if (addAvailableRookMove(result, cell)) {
                break;
            }
        }

        return result;
    }

    private List<PointDTO> getMovesForQueen(CellDTO selectedCell) {
        List<PointDTO> result = new ArrayList<>();
        //getMovesForRook + getMovesForBishop + distinct
        return result;
    }

    private List<PointDTO> getMovesForKing(CellDTO selectedCell) {
        List<PointDTO> result = new ArrayList<>();

        return result;
    }

    private CellDTO getSelectedCell(int rowIndex, int columnIndex) {
        if (rowIndex < 0 || rowIndex > 7 || columnIndex < 0 || columnIndex > 7) {
            return null;
        }
        return piecesMatrix.get(rowIndex).get(columnIndex);
    }

    private Side getEnemySide(CellDTO selectedCell) {
        Side enemySide = Side.black;
        if (selectedCell.getSide() == Side.black) {
            enemySide = Side.white;
        }
        return enemySide;
    }

    private List<PointDTO> getRandomMoves() {
        return new ArrayList<PointDTO>() {{
            add(new PointDTO(3, 3));
            add(new PointDTO(4, 3));
            add(new PointDTO(rnd(), rnd()));
        }};
    }

    private static int rnd() {
        return Utils.generateRandomInt(4, 7);
    }

    @Override
    public void setPiecesMatrix(List<List<CellDTO>> piecesMatrix) {
        this.piecesMatrix = piecesMatrix;
    }
}
