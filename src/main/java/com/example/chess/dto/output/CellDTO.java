package com.example.chess.dto.output;

import com.example.chess.dto.PointDTO;
import com.example.chess.enums.PieceType;
import com.example.chess.enums.Side;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 19.01.2018.
 */
@Getter
@Setter
public class CellDTO extends PointDTO {

    private Side side;
    private PieceType piece;
    private boolean available;
    private boolean selected;

    public CellDTO(Integer row, Integer column) {
        this.rowIndex = row;
        this.columnIndex = column;
    }
}
