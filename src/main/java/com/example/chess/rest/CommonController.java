package com.example.chess.rest;

import com.example.chess.App;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 19.01.2018.
 */
@RestController
@RequestMapping("/api")
public class CommonController {

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok(App.APP_VERSION);
    }
}
