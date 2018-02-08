package com.example.chess.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 02.02.2018.
 */
@Entity
@Getter
@Setter
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long gameId;

    private Long userId;

    private String sessionId;

    @Column(nullable = false)
    private Boolean isWhite;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isShortCastlingAvailable = true;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isLongCastlingAvailable = true;
}
