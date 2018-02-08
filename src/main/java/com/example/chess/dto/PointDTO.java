package com.example.chess.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 01.02.2018.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {
    protected Integer rowIndex;
    protected Integer columnIndex;
}
