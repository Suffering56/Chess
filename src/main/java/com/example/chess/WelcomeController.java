package com.example.chess;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 10.01.2018.
 */
@Controller
public class WelcomeController {

    // inject via application.properties
    @Value("${welcome.message:test}")
    private String message = "Default message";

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("message", this.message);
        model.put("index", 8);
        return "welcome";
    }

    @RequestMapping("/other/{index}")
    public String other(@PathVariable("index") int pathIndex, Map<String, Object> model) {
        model.put("index", pathIndex);
        return "other";
    }
}
