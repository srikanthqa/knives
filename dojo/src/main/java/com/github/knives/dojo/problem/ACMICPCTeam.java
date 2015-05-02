package com.github.knives.dojo.problem;

import java.math.BigInteger;
import java.util.Scanner;

public class ACMICPCTeam {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final int N = scanner.nextInt();
        final int M = scanner.nextInt();
        
        final BigInteger[] person = new BigInteger[N];
        for (int i = 0; i < N; i++) {
            person[i] = scanner.nextBigInteger(2);
        }
        
        // O(N*N*M)
        // could we do it better ?
        int maxTopic = 0;
        int maxTeam = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                int topicCnt = person[i].or(person[j]).bitCount();
                if (topicCnt > maxTopic) {
                    maxTopic = topicCnt;
                    maxTeam = 1;
                } else if (topicCnt == maxTopic) {
                    maxTeam++;
                }
            }
        }
        
        System.out.println(maxTopic);
        System.out.println(maxTeam);
    }
}
