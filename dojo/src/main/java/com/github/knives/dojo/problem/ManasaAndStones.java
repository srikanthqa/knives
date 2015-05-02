package com.github.knives.dojo.problem;

import java.util.Scanner;
import java.util.TreeSet;

public class ManasaAndStones {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int T = scanner.nextInt();
        for (int t = 0; t < T; t++) {
            final int N = scanner.nextInt();
            final int[] diff = new int[2];
            diff[0] = scanner.nextInt();
            diff[1] = scanner.nextInt();
            if (diff[0] > diff[1]) {
                int tmp = diff[0];
                diff[0] = diff[1];
                diff[1] = tmp;
            }
            
            // TreeSet is more efficient in this problem then LinkedList
            // although LinkedList provided O(1) for add, but contains
            // would cost O(n)
            // TreeSet provides free find for add() for O(log n)
            TreeSet<Integer> currentStones = new TreeSet<Integer>();
            currentStones.add(0);
            
            for (int i = 1; i < N; i++) {
                
                final TreeSet<Integer> nextStones = new TreeSet<Integer>();
                for (Integer stone : currentStones) {
                    nextStones.add(stone + diff[0]);
                    nextStones.add(stone + diff[1]);
                }
                
                // swap
                currentStones = nextStones;
            }
            
            StringBuilder builder = new StringBuilder(10000);
            for (Integer stone : currentStones) {
                builder.append(stone).append(" ");
            }
            System.out.println(builder.toString());
            
        }
    }
}
