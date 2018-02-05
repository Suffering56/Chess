package com.example.chess.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Valery Peschanyy <p.v.s.oren@gmail.com> on 29.01.2018.
 */
public class Utils {

    public static String generateRandomString() {
        RandomStringGenerator randomStringGenerator =
                new RandomStringGenerator.Builder()
                        .withinRange('0', 'z')
                        .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                        .build();
        return randomStringGenerator.generate(25);
    }

    public static int generateRandomInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static String convertStackTraceToString(StackTraceElement[] stackTraceElements) {
        List<String> collect = Arrays.stream(stackTraceElements)
                .map(StackTraceElement::toString)
                .collect(Collectors.toList());
        return StringUtils.join(collect);
    }
}
