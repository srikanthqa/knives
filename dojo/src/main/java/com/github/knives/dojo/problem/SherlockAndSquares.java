package com.github.knives.dojo.problem;

import java.util.Scanner;

public class SherlockAndSquares {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int T = scanner.nextInt();
        for (int t = 0; t < T; t++) {
            final int A = scanner.nextInt();
            final int B = scanner.nextInt();
            final int sqrtA = (int)Math.sqrt(A);
            final int sqrtB = (int)Math.sqrt(B);
            final int inclusiveOffset = sqrtA*sqrtA == A ? 1:0;
            System.out.println(sqrtB - sqrtA + inclusiveOffset);
        }
    }
}
