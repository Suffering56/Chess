package com.example.chess.web;

import com.example.chess.dto.input.MoveStartDTO;
import com.example.chess.dto.input.SideChooseDTO;
import com.example.chess.dto.output.CellDTO;
import com.example.chess.dto.output.ParamsDTO;
import com.example.chess.dto.output.ParamsPlayerDTO;
import com.example.chess.entity.Game;
import com.example.chess.entity.Player;
import com.example.chess.repository.GameRepository;
import com.example.chess.repository.PlayerRepository;
import com.example.chess.service.GameService;
import com.example.chess.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 09.01.2018.
 */
@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public GameController(GameService gameService, GameRepository gameRepository, PlayerRepository playerRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/start")
    public Game createNewGame() {
        return gameRepository.save(new Game());
    }

    @GetMapping("/{gameId}/player/side")
    public ParamsPlayerDTO getSide(@PathVariable("gameId") Long gameId,
                                   HttpServletRequest request) {

        ParamsPlayerDTO result = new ParamsPlayerDTO();
        Player byGameIdAndSessionId = playerRepository.findByGameIdAndSessionId(gameId, request.getSession().getId());

        if (byGameIdAndSessionId != null) {
            result.setIsWhite(byGameIdAndSessionId.getIsWhite());
        } else {
            Player byGameId = playerRepository.findFirstByGameId(gameId);
            if (byGameId != null) {
                result.setIsViewer(true);
                result.setIsWhite(true);
            } else {
                return null;
            }
        }

        return result;
    }

    @PostMapping("/{gameId}/player/side")
    public Player setSide(@PathVariable("gameId") Long gameId,
                          @RequestBody SideChooseDTO dto,
                          HttpServletRequest request) {

        Player player = new Player();
        player.setGameId(gameId);
        player.setIsWhite(dto.getIsWhite());
        player.setSessionId(request.getSession().getId());
        return playerRepository.save(player);
    }

    @GetMapping("/{gameId}/start/arrangement")
    public ParamsDTO getStartArrangement(@PathVariable("gameId") long gameId) {
        List<List<CellDTO>> cells = gameService.createStartArrangementPieceMatrix();

        ParamsDTO result = new ParamsDTO();
        result.setCells(cells);
        result.setGameId(gameId);

        return result;
    }

    @PostMapping("/{gameId}/move")
    public ParamsDTO getAvailableMoves(@PathVariable("gameId") Long gameId,
                                       @RequestBody MoveStartDTO dto) {
//        Game game = gameRepository.findOne(gameId);
        List<List<CellDTO>> cells = gameService.createStartArrangementPieceMatrix();

        cells.get(3).get(3).setAvailable(true);
        cells.get(4).get(3).setAvailable(true);
        cells.get(rnd()).get(rnd()).setAvailable(true);
        cells.get(dto.getSelectedRow()).get(dto.getSelectedColumn()).setSelected(true);

        ParamsDTO result = new ParamsDTO();
        result.setGameId(gameId);
        result.setCells(cells);

        return result;
    }

    private static int rnd() {
        return Utils.generateRandomInt(4, 7);
    }
}
