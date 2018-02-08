package com.example.chess.dto.input;

import com.example.chess.dto.PointDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 08.02.2018.
 */
@Setter
@Getter
@NoArgsConstructor
public class MoveDTO {

    private PointDTO from;
    private PointDTO to;
}
