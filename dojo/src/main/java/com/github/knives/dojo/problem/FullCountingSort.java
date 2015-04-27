package com.github.knives.dojo.problem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FullCountingSort {

    public static void main(String[] args) throws IOException {        
    	// This is to beat slow IO
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        final int[] buckets = new int[100];
        final int[] helperArray = new int[100];

        final int N = Integer.valueOf(reader.readLine());
        final int[] oriNum = new int[N];
        final String[] oriStr = new String[N];
        final int[] sortedDatum = new int[N];

        for (int i = 0; i < N; i++) {
            final String[] input = reader.readLine().split(" ");
            oriNum[i] = Integer.valueOf(input[0]);
            oriStr[i] = input[1];
            buckets[oriNum[i]]++;
        }
        
        for (int i = 1; i < 100; i++) {
            helperArray[i] = helperArray[i-1] + buckets[i-1];
        }
               
        for (int i = 0; i < N; i++) {
            sortedDatum[helperArray[oriNum[i]]] = i;

            // The trick to use counting sort
            // bump the index by one so the next item goes to correct place
            helperArray[oriNum[i]]++;
        }
        
        // This is to beat slow IO
        StringBuilder sb = new StringBuilder(20000);

        for (int i = 0; i < N; i++) {
            if (sortedDatum[i] < N/2) {
                sb.append("- ");
            } else {
                sb.append(oriStr[sortedDatum[i]]);
                sb.append(" ");
            }
        }
        
        System.out.println(sb.toString());
    }
}
