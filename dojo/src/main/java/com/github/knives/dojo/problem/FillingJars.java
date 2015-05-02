package com.github.knives.dojo.problem;

import java.util.Scanner;

public class FillingJars {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final long N = scanner.nextInt();
        final long M = scanner.nextInt();
        long totalCandy = 0;
        for (int i = 0; i < M; i++) {
            final long a = scanner.nextLong();
            final long b = scanner.nextLong();
            final long k = scanner.nextLong();
            totalCandy += (b - a + 1) * k;
        }
        
        System.out.println(totalCandy / N);
    }
}
