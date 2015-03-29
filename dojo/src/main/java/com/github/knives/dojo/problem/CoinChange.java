package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * How many different ways can you make change for an amount, given a list of coins?
 * In this problem, your code will need to efficiently compute the answer.
 *
 * Problem Statement
 *
 * Write a program that, given
 *
 * An amount N and types of infinite available coins M
 * A list of M coins - C={C1,C2,C3,..,CM}
 * Prints out how many different ways you can make change from the coins to STDOUT.
 *
 * The problem can be formally stated:
 *
 * Given a value N, if we want to make change for N cents, and we have infinite supply of each of C={C1,C2,…,CM} valued coins, how many ways can we make the change? The order of coins doesn’t matter.
 *
 * Constraints
 *
 * 1≤Ci≤50
 * 1≤N≤250
 * 1≤M≤50
 * The list of coins will contain distinct integers.
 */
public interface CoinChange {

    public static long compute(int[] coins, int change) {
        final long[] changes = new long[change+1];
        changes[0] = 1;

        // sort in increasing order
        Arrays.sort(coins);

        for (int i = 0; i < coins.length; i++) {
            final int coin = coins[i];
            // set direct change
            if (coin <= change) {
                changes[coin] += 1;
            }

            // from coin+1 change, start compute way of making change
            for (int j = coin + 1; j <= change; j++) {
                changes[j] += changes[j - coin];
            }
        }

        //System.out.println(Arrays.toString(changes));

        return changes[change];
    }
}
