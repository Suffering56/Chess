package com.example.chess.dto.input;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 01.02.2018.
 */
@Getter
@Setter
public class MoveStartDTO {

    private Long position;
    private Integer selectedRow;
    private Integer selectedColumn;
}
