package com.github.knives.dojo.utils;

import java.util.stream.IntStream;

public interface MathUtils {
    public static int max(int value1, int ... values) {
        return IntStream.concat(IntStream.of(value1), IntStream.of(values))
                .max()
                .getAsInt();
    }
}
