package com.example.chess;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 09.01.2018.
 */
@RestController
@RequestMapping(path = "/api")
public class ExampleController {

    @GetMapping(path = "/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok("v.0.0.12");
    }
}
