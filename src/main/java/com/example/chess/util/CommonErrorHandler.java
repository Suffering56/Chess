package com.example.chess.util;

import com.example.chess.dto.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 01.02.2018.
 */
@RestControllerAdvice
public class CommonErrorHandler {

    @ExceptionHandler(NavigationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionDTO authenticationError(NavigationException e) {
        return new ExceptionDTO(HttpStatus.NOT_FOUND, e.getMessage());
    }
}
