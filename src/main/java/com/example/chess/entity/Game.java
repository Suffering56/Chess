package com.example.chess.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 29.01.2018.
 */
@Entity
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ColumnDefault("0")
    @Column(nullable = false)
    private Integer position = 0;
}
