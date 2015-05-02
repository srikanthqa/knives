package com.github.knives.dojo.problem;

import java.util.Scanner;

public class TaumAndBday {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final int T = scanner.nextInt();
        for (int t = 0; t < T; t++) {
            final long B = scanner.nextLong();
            final long W = scanner.nextLong();
            final long X = scanner.nextLong();
            final long Y = scanner.nextLong();
            final long Z = scanner.nextLong();
            
            System.out.println(B * cost(X, Y, Z) + W * cost(Y, X, Z));
        }
    }
    
    static long cost(long costCurrentGift, long costOtherGift, long costConverter) {
        return Math.min(costCurrentGift, costOtherGift + costConverter);
    }
}
