package com.example.chess.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 06.02.2018.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerParamsDTO {

    private Boolean isWhite;
    private Boolean isViewer = false;
}
