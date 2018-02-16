package com.example.chess.service;

import com.example.chess.dto.PointDTO;
import com.example.chess.dto.output.CellDTO;

import java.util.List;

public interface PieceService {

    List<PointDTO> getAvailableMoves(CellDTO selectedCell);

    void setPiecesMatrix(List<List<CellDTO>> piecesMatrix);
}
