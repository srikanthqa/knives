package com.github.knives.dojo.problem;

import java.util.Scanner;

public class QuickSort2 {
    public static void partition(int[] array, int startIndex, int endExclusiveIndex) {
        if (endExclusiveIndex <= startIndex+1) return;

        int pivotalIndex = startIndex;
        for (int i = startIndex+1; i < endExclusiveIndex; i++) {
            if (array[i] <= array[pivotalIndex]) {
                for (int j = i; j > pivotalIndex; j--) {
                    swap(array, j, j-1);
                }
                pivotalIndex++;
            }
        }
        
        partition(array, startIndex, pivotalIndex);
        partition(array, pivotalIndex+1, endExclusiveIndex);

        printArray(array, startIndex, endExclusiveIndex);
	}
	
    public static void swap(int[] array, int i, int j) {
		if (i == j) return;
		int tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
    
        
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int N = scanner.nextInt();
        final int[] array = new int[N];
        for (int i = 0; i < N; i++) {
            array[i] = scanner.nextInt();
        }
        partition(array, 0, N);
    }
    
    public static void printArray(int[] array, int startIndex, int endExclusiveIndex) {
        for (int i = startIndex; i < endExclusiveIndex; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
