package com.github.knives.dojo.problem;

public interface SmallestMultiple {
    public static long compute(long n) {
        long product = 1;
        for (long i = 2; i <= n; i++) {
            product = lcm(product, i);
        }
        return product;
    }

    public static long gcd(long a, long b) {
        while (a != b) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }

        return a;
    }

    public static long lcm(long a, long b) {
        return a / gcd(a,b) * b;
    }
}
