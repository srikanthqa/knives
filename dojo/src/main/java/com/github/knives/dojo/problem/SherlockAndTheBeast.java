package com.github.knives.dojo.problem;

import java.util.Scanner;

public class SherlockAndTheBeast {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int T = scanner.nextInt();
        for (int t = 0; t < T; t++) {
            final int N = scanner.nextInt();
            final int maxNum5 = N / 3;
            
            boolean findSolution = false;
            for (int i = maxNum5; i >= 0; i--) {
                final int maxNum3 = N - (i*3);
                if (maxNum3 == 0 || maxNum3 % 5 == 0) {
                    System.out.println(construct(maxNum3, i*3));
                    findSolution = true;
                    break;
                }
            }
            
            if (!findSolution) System.out.println(-1);
        }
    }
    
    static String construct(int num3, int num5) {
        StringBuilder builder = new StringBuilder(num3 + num5);
        for (int i = 0; i < num5; i++) {
            builder.append('5');
        }
        
        for (int i = 0; i < num3; i++) {
            builder.append('3');
        }
        
        return builder.toString();
    }
}
