package com.example.chess.web;

import com.example.chess.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 10.01.2018.
 */
@Controller
public class NavigationController {

    @RequestMapping("/")
    public String redirect() {
        return "redirect:/game/" + Utils.randInt(1,100);
    }

    @RequestMapping("/game/{gameId}")
    public String game(@PathVariable("gameId") String gameId, Map<String, Object> model) {
        model.put("gameId", gameId);
        return "game";
    }
}
