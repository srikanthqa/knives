package com.github.knives.dojo.problem;

import java.util.Scanner;

public class SherlockAndGCD {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int T = scanner.nextInt();
        for (int t = 0; t < T; t++) {
            final int N = scanner.nextInt();
            final int[] array = new int[N];
            for (int i = 0; i < N; i++) {
                array[i] = scanner.nextInt();
            }
            
            boolean subsetExist = false;
            
            for (int i = 1; i < N; i++) {
                if (array[i] != array[i-1] && gcd(array[i], array[i-1]) == 1) {
                    subsetExist = true;
                    break;
                }
            }
            
            if (subsetExist) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
            
        }
    }
    
    static int gcd(int a, int b) {
        while (a != b) {
            if (a > b)
                a = a - b;
            else
                b = b - a;
        }

        return a;
    } 
}
