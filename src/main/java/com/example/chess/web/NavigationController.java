package com.example.chess.web;

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
//        return "redirect:/game/" + Utils.generateRandomInt(1,100);
        return "index";
    }

    @RequestMapping("/game/{gameId}")
    public String game(@PathVariable("gameId") String gameId, Map<String, Object> model) {
        return "index";
    }

    @RequestMapping("/game/{gameId}/move/{moveIndex}")
    public String gameAndMove(@PathVariable("gameId") String gameId, Map<String, Object> model,
                              @PathVariable("moveIndex") String moveIndex) {
        return "index";
    }
}
