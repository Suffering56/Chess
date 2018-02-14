package com.example.chess.service.impl;

import com.example.chess.aspects.ProfileExecutionTime;
import com.example.chess.aspects.SynchronizeGame;
import com.example.chess.dto.PointDTO;
import com.example.chess.dto.input.MoveDTO;
import com.example.chess.dto.output.CellDTO;
import com.example.chess.dto.output.ParamsDTO;
import com.example.chess.entity.Game;
import com.example.chess.entity.History;
import com.example.chess.enums.PieceType;
import com.example.chess.enums.Side;
import com.example.chess.exceptions.GameNotMatchedException;
import com.example.chess.repository.GameRepository;
import com.example.chess.repository.HistoryRepository;
import com.example.chess.service.GameService;
import com.example.chess.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private HistoryRepository historyRepository;

    private List<List<CellDTO>> piecesMatrix;
    private Game game;
    private int currentMatrixPosition = 0;

    @Override
    public Game findGameById(long gameId) {
        game = gameRepository.findOne(gameId);
        piecesMatrix = createStartArrangement();
        return game;
    }

    @Override
    @SynchronizeGame
    @ProfileExecutionTime
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
    @Transactional
    public ParamsDTO applyMove(long gameId, MoveDTO dto) throws GameNotMatchedException {
        checkGame(gameId);
        int newPosition = game.getPosition() + 1;

        movePiece(dto.getFrom().getRowIndex(), dto.getFrom().getColumnIndex(),
                dto.getTo().getRowIndex(), dto.getTo().getColumnIndex());

        clearMatrix();

        History historyItem = new History();
        historyItem.setGameId(gameId);
        historyItem.setRowIndexFrom(dto.getFrom().getRowIndex());
        historyItem.setColumnIndexFrom(dto.getFrom().getColumnIndex());
        historyItem.setRowIndexTo(dto.getTo().getRowIndex());
        historyItem.setColumnIndexTo(dto.getTo().getColumnIndex());
        historyItem.setPosition(newPosition);

        game.setPosition(newPosition);

        historyRepository.save(historyItem);
        gameRepository.save(game);

        ParamsDTO result = new ParamsDTO();
        result.setPiecesMatrix(piecesMatrix);
        result.setGame(game);

        return result;
    }

    @Override
    public ParamsDTO getArrangementByPosition(long gameId, int position) {
        if (position != currentMatrixPosition) {
            piecesMatrix = createPiecesMatrixByPosition(gameId, position);
            currentMatrixPosition = position;
        }

        return createParamsDTO();
    }

    private void checkGame(long gameId) throws GameNotMatchedException {
        if (gameId != game.getId()) {
            throw new GameNotMatchedException();
        }
    }

    private void movePiece(int rowFrom, int columnFrom, int rowTo, int columnTo) {
        CellDTO from = piecesMatrix.get(rowFrom).get(columnFrom);
        CellDTO to = piecesMatrix.get(rowTo).get(columnTo);

        to.setSide(from.getSide());
        to.setPiece(from.getPiece());

        from.setSide(null);
        from.setPiece(null);
    }

    private List<List<CellDTO>> createPiecesMatrixByPosition(long gameId, int position) {
        List<List<CellDTO>> result = createStartArrangement();

        if (position > 0) {
            List<History> historyList = historyRepository.findByGameIdAndPositionLessThanEqualOrderByPosition(gameId, position);
            for (History item : historyList) {
                movePiece(item.getRowIndexFrom(), item.getColumnIndexFrom(),
                        item.getRowIndexTo(), item.getColumnIndexTo());
            }
        }

        return result;
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

        final int BOARD_SIZE = 8;
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
