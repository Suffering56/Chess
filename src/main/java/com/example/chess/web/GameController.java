package com.example.chess.web;

import com.example.chess.dto.CellDTO;
import com.example.chess.dto.GameDTO;
import com.example.chess.dto.MoveInputDTO;
import com.example.chess.dto.SideChooseDTO;
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

    @PostMapping("/{gameId}/player/register")
    public Player registerPlayer(@PathVariable("gameId") Long gameId,
                               @RequestBody SideChooseDTO dto,
                               HttpServletRequest request) {

        Player player = new Player();
        player.setGameId(gameId);
        player.setIsWhite(dto.getIsWhite());
        player.setSession(request.getSession().getId());
        return playerRepository.save(player);
    }

    @GetMapping("/{gameId}/start/arrangement")
    public GameDTO getStartArrangement(@PathVariable("gameId") long gameId) {
        List<List<CellDTO>> cells = gameService.getStartArrangement();

        GameDTO result = new GameDTO();
        result.setCells(cells);
        result.setGameId(gameId);

        return result;
    }

    @PostMapping("/{gameId}/move")
    public GameDTO getAvailableMoves(@PathVariable("gameId") Long gameId,
                                     @RequestBody MoveInputDTO dto) {
//        Game game = gameRepository.findOne(gameId);
        List<List<CellDTO>> cells = gameService.getStartArrangement();

        cells.get(3).get(3).setAvailable(true);
        cells.get(4).get(3).setAvailable(true);
        cells.get(rnd()).get(rnd()).setAvailable(true);
        cells.get(dto.getSelectedRow()).get(dto.getSelectedColumn()).setSelected(true);

        GameDTO result = new GameDTO();
        result.setGameId(gameId);
        result.setCells(cells);

        return result;
    }

    private static int rnd() {
        return Utils.generateRandomInt(4, 7);
    }
}
