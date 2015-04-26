package com.github.knives.dojo.problem;

import java.util.Scanner;

public class QuickSort3 {
    public static void partition(int[] array, int startIndex, int endExclusiveIndex) {
        if (endExclusiveIndex <= startIndex+1) return;

        int storeIndex = startIndex;
        
        final int pivotalIndex = endExclusiveIndex-1;
        
        for (int i = startIndex; i < endExclusiveIndex-1; i++) {
            if (array[i] < array[pivotalIndex]) {
                swap(array, i, storeIndex);
                storeIndex++;
            }
        }
        
        swap(array, storeIndex, pivotalIndex);
        printArray(array, 0, array.length);

        partition(array, startIndex, storeIndex);
        partition(array, storeIndex+1, endExclusiveIndex);

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
