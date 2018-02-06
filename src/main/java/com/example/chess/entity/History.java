package com.example.chess.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 29.01.2018.
 */
@Entity
@Getter
@Setter
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long gameId;

    @Column(nullable = false)
    private Integer position;

    @Column(nullable = false)
    private Integer rowIndexFrom;

    @Column(nullable = false)
    private Integer columnIndexFrom;

    @Column(nullable = false)
    private Integer rowIndexTo;

    @Column(nullable = false)
    private Integer columnIndexTo;
}
