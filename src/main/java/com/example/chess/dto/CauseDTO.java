package com.example.chess.dto;

import com.example.chess.util.Utils;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 05.02.2018.
 */
@Setter
@Getter
public class CauseDTO {

    protected String errorMessage;
    protected String stackTrace;

    public CauseDTO(Throwable e) {
        errorMessage = e.getMessage();
        stackTrace = Utils.convertStackTraceToString(e.getStackTrace());
    }
}
