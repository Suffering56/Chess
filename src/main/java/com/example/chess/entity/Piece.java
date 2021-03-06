package com.example.chess.entity;

import com.example.chess.enums.PieceType;
import com.example.chess.enums.Side;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 29.01.2018.
 */
@Entity
@Getter
@Setter
public class Piece {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Side side;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PieceType type;

}
