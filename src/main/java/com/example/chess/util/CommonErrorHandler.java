package com.example.chess.util;

import com.example.chess.dto.output.exceptions.ExceptionDTO;
import com.example.chess.exceptions.GameNotMatchedException;
import org.hibernate.exception.ConstraintViolationException;
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

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ExceptionDTO sqlExceptionHandle(ConstraintViolationException e) {
        System.out.println("\r\norg.hibernate.exception.ConstraintViolationException.constraintName = " + e.getConstraintName());
        e.printStackTrace();
        return new ExceptionDTO(e);
    }

    @ExceptionHandler(NavigationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ExceptionDTO navigationExceptionHandle(NavigationException e) {
        return new ExceptionDTO(e);
    }

    @ExceptionHandler(GameNotMatchedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ExceptionDTO navigationExceptionHandle(GameNotMatchedException e) {
        return new ExceptionDTO(e);
    }
}
