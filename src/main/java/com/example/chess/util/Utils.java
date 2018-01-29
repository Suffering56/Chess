package com.example.chess.util;

import java.util.Random;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 29.01.2018.
 */
public class Utils {

    public static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
