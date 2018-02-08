package com.example.chess.service.impl;

import com.example.chess.dto.PointDTO;
import com.example.chess.dto.input.MoveDTO;
import com.example.chess.dto.output.CellDTO;
import com.example.chess.dto.output.ParamsDTO;
import com.example.chess.entity.Game;
import com.example.chess.enums.PieceType;
import com.example.chess.enums.Side;
import com.example.chess.repository.GameRepository;
import com.example.chess.service.GameService;
import com.example.chess.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 19.01.2018.
 */
@Service
@SessionScope
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    private static final int BOARD_SIZE = 8;
    private List<List<CellDTO>> piecesMatrix;
    private Game game;
    private int servicePosition = 0;

    @Override
    public Game findGameById(long gameId) {
        game = gameRepository.findOne(gameId);
        piecesMatrix = createStartArrangement();
        return game;
    }

    @Override
    public Game createNewGame() {
        game = gameRepository.save(new Game());
        piecesMatrix = createStartArrangement();
        return game;
    }

    @Override
    public List<PointDTO> getAvailableMoves(long gameId, PointDTO selectedCell) {
        return new ArrayList<PointDTO>() {{
            add(new PointDTO(3, 3));
            add(new PointDTO(4, 3));
            add(new PointDTO(rnd(), rnd()));
        }};
    }

    @Override
    public ParamsDTO applyMove(long gameId, MoveDTO dto) {
        CellDTO from = piecesMatrix.get(dto.getFrom().getRowIndex()).get(dto.getFrom().getColumnIndex());
        CellDTO to = piecesMatrix.get(dto.getTo().getRowIndex()).get(dto.getTo().getColumnIndex());

        to.setSide(from.getSide());
        to.setPiece(from.getPiece());

        from.setSide(null);
        from.setPiece(null);

        clearMatrix();

        game.setPosition(game.getPosition() + 1);
        gameRepository.save(game);

        ParamsDTO result = new ParamsDTO();

        result.setPiecesMatrix(piecesMatrix);
        result.setGame(game);

        return result;
    }

    @Override
    public ParamsDTO getArrangementByPosition(long gameId, int position) {
        if (position != servicePosition) {
            piecesMatrix = createStartArrangement();
            //TODO: load history of moves and change matrix

            servicePosition = position;
        }

        return createParamsDTO();
    }

    @Override
    public Game getGame() {
        return game;
    }

    private ParamsDTO createParamsDTO() {
        ParamsDTO result = new ParamsDTO();
        result.setGame(game);
        result.setPiecesMatrix(piecesMatrix);
        return result;
    }

    private void clearMatrix() {
        for (List<CellDTO> row : piecesMatrix) {
            for (CellDTO cell : row) {
                cell.setAvailable(false);
                cell.setSelected(false);
            }
        }
    }

    private static int rnd() {
        return Utils.generateRandomInt(4, 7);
    }

    private List<List<CellDTO>> createStartArrangement() {
        List<List<CellDTO>> result = new ArrayList<>();

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
            result.add(cells);
        }

        return result;
    }
}
