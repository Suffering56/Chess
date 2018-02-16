package com.example.chess.service.impl;

import com.example.chess.dto.PointDTO;
import com.example.chess.dto.output.CellDTO;
import com.example.chess.enums.Side;
import com.example.chess.service.PieceService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PiecesServiceImpl implements PieceService {

    private List<List<CellDTO>> piecesMatrix;
    private CellDTO selectedCell;
    private int selectedRow;
    private int selectedColumn;
    private Side alliedSide;
    private Side enemySide;

    @Override
    public List<PointDTO> getAvailableMoves(CellDTO selectedCell) {
        this.selectedCell = selectedCell;
        selectedRow = selectedCell.getRowIndex();
        selectedColumn = selectedCell.getColumnIndex();
        alliedSide = selectedCell.getSide();
        enemySide = getEnemySide(selectedCell);

        switch (selectedCell.getPiece()) {
            case pawn: {
                return getMovesForPawn();
            }
            case knight: {
                return getMovesForKnight();
            }
            case bishop: {
                return getMovesForBishop();
            }
            case rook: {
                return getMovesForRook();
            }
            case queen: {
                return getMovesForQueen();
            }
            case king: {
                return getMovesForKing();
            }
        }

        return new ArrayList<>();
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    private List<PointDTO> getMovesForPawn() {
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

    private List<PointDTO> getMovesForKnight() {
        List<PointDTO> result = new ArrayList<>();

        //TODO: реализовать ходы коня

        return result;
    }

    private List<PointDTO> getMovesForBishop() {
        List<PointDTO> result = new ArrayList<>();

        addAvailableMovesByVector(result, 1, 1);
        addAvailableMovesByVector(result, -1, 1);
        addAvailableMovesByVector(result, 1, -1);
        addAvailableMovesByVector(result, -1, -1);

        return result;
    }

    private List<PointDTO> getMovesForRook() {
        List<PointDTO> result = new ArrayList<>();

        addAvailableMovesByVector(result, 1, 0);
        addAvailableMovesByVector(result, -1, 0);
        addAvailableMovesByVector(result, 0, 1);
        addAvailableMovesByVector(result, 0, -1);

        return result;
    }

    private List<PointDTO> getMovesForQueen() {
        List<PointDTO> result = new ArrayList<>();

        result.addAll(getMovesForRook());
        result.addAll(getMovesForBishop());

        return result;
    }

    private List<PointDTO> getMovesForKing() {
        List<PointDTO> result = new ArrayList<>();

        addAvailableMovesByVector(result, 1, 0, 1);
        addAvailableMovesByVector(result, -1, 0, 1);
        addAvailableMovesByVector(result, 0, 1, 1);
        addAvailableMovesByVector(result, 0, -1, 1);
        addAvailableMovesByVector(result, 1, 1, 1);
        addAvailableMovesByVector(result, -1, 1, 1);
        addAvailableMovesByVector(result, 1, -1, 1);
        addAvailableMovesByVector(result, -1, -1, 1);

        //TODO: реализовать рокировку

        return result;
    }

    private void addAvailableMovesByVector(List<PointDTO> resultMovesList, int rowVector, int columnVector) {
        addAvailableMovesByVector(resultMovesList, rowVector, columnVector, 7);
    }

    private void addAvailableMovesByVector(List<PointDTO> resultMovesList, int rowVector, int columnVector, int vectorLength) {
        for (int i = 1; i < vectorLength + 1; i++) {
            CellDTO cell = getSelectedCell(selectedRow + rowVector * i, selectedColumn + columnVector * i);
            if (addSingleAvailableMoveForVector(resultMovesList, cell)) {
                break;
            }
        }
    }

    private boolean addSingleAvailableMoveForVector(List<PointDTO> resultMovesList, CellDTO cell) {
        if (cell == null || cell.getSide() == alliedSide) {
            return true;
        }

        resultMovesList.add(cell);

        if (cell.getSide() == enemySide) {
            return true;
        }
        return false;
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

    @Override
    public void setPiecesMatrix(List<List<CellDTO>> piecesMatrix) {
        this.piecesMatrix = piecesMatrix;
    }
}
