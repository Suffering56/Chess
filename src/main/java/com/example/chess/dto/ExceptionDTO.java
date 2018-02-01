package com.example.chess.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 01.02.2018.
 */
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {

    public HttpStatus httpStatus;
    public String errorMessage;
}
