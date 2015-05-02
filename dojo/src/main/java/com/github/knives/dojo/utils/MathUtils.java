package com.github.knives.dojo.utils;

import java.util.stream.IntStream;

public interface MathUtils {
    static int max(int value1, int ... values) {
        return IntStream.concat(IntStream.of(value1), IntStream.of(values))
                .max()
                .getAsInt();
    }
    
    static long gcd(long a, long b) {
        while (a != b) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }

        return a;
    }

    static long lcm(long a, long b) {
        return a / gcd(a,b) * b;
    }
}
