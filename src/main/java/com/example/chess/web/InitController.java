package com.example.chess.web;

import com.example.chess.dto.input.SideChooseDTO;
import com.example.chess.dto.output.ParamsDTO;
import com.example.chess.dto.output.ParamsPlayerDTO;
import com.example.chess.entity.Game;
import com.example.chess.entity.Player;
import com.example.chess.repository.PlayerRepository;
import com.example.chess.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 09.01.2018.
 */
@RestController
@RequestMapping("/api/init")
public class InitController {

    @Autowired
    private GameService gameService;
    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping
    public Game createNewGame() {
        return gameService.createNewGame();
    }

    @GetMapping("/{gameId}")
    public Game checkGame(@PathVariable("gameId") long gameId) {
        return gameService.findGameById(gameId);
    }

    @GetMapping("/{gameId}/side")
    public ParamsPlayerDTO getSide(@PathVariable("gameId") Long gameId,
                                   HttpServletRequest request) {

        List<Player> gamePlayers = playerRepository.findByGameId(gameId);
        Optional<Player> foundPlayer = gamePlayers.stream()
                .filter(player -> request.getSession().getId().equals(player.getSessionId()))
                .findFirst();

        if (foundPlayer.isPresent()) {
            return new ParamsPlayerDTO(foundPlayer.get().getIsWhite(), false);
        }

        if (gamePlayers.size() == 0) {
            //choose side
            return new ParamsPlayerDTO(null, false);
        } else if (gamePlayers.size() == 1) {
            Player enemyPlayer = savePlayer(gameId, request.getSession().getId(), !gamePlayers.get(0).getIsWhite());
            //enemy
            return new ParamsPlayerDTO(enemyPlayer.getIsWhite(), false);
        } else if (gamePlayers.size() == 2) {
            //viewer
            return new ParamsPlayerDTO(null, true);
        } else {
            throw new RuntimeException("WTF: gamePlayers.size = " + gamePlayers.size());
        }
    }

    @PostMapping("/{gameId}/side")
    public Player setSide(@PathVariable("gameId") Long gameId,
                          @RequestBody SideChooseDTO dto,
                          HttpServletRequest request) {

        return savePlayer(gameId, request.getSession().getId(), dto.getIsWhite());
    }

    private Player savePlayer(long gameId, String sessionId, boolean isWhite) {
        Player player = new Player();
        player.setGameId(gameId);
        player.setIsWhite(isWhite);
        player.setSessionId(sessionId);
        return playerRepository.save(player);
    }

    @GetMapping("/{gameId}/arrangement/{position}")
    public ParamsDTO getArrangementByPosition(@PathVariable("gameId") long gameId,
                                              @PathVariable("position") int position) {

        return gameService.getArrangementByPosition(gameId, position);
    }


}
