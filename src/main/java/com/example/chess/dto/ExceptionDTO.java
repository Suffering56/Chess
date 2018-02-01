package com.example.chess.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 01.02.2018.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDTO {

    private HttpStatus httpStatus;
    private String errorMessage;
}
