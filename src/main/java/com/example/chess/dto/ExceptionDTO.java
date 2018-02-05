package com.example.chess.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 01.02.2018.
 */

@Setter
@Getter
public class ExceptionDTO extends CauseDTO {

    protected CauseDTO cause;

    public ExceptionDTO(Throwable e) {
        super(e);
        if (e.getCause() != null) {
            cause = new CauseDTO(e.getCause());
        }
    }
}

