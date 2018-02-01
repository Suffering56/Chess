package com.example.chess.web;

import com.example.chess.util.NavigationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 10.01.2018.
 */
@Controller
public class NavigationController {

    private static final String VIEW = "index";

    @RequestMapping({
            "/",
            "/game/{gameId}",
            "/game/{gameId}/move/{moveId}"
    })
    public String redirect(@PathVariable(value = "gameId", required = false) String gameId,
                           @PathVariable(value = "moveId", required = false) String moveId) throws NavigationException {
        if (gameId != null && !StringUtils.isNumeric(gameId)) {
            throw new NavigationException("<gameId> must be a number");
        }
        if (moveId != null && !StringUtils.isNumeric(moveId)) {
            throw new NavigationException("<moveId> must be a number");
        }
        return VIEW;
    }
}
