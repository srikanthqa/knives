package com.github.knives.dojo.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
 */
public interface CoinChange {

    public static int compute(int[] coins, int change) {
        final ArrayList<Set<String>> changes = new ArrayList<Set<String>>(change+1);
        changes.add(new HashSet<String>() {
            {
                add("0");
            }
        });

        for (int i = 1; i <= change; i++) {
            changes.add(new HashSet<String>());

            for (int j = coins.length - 1; j >= 0; j--) {
                final int coin = coins[j];

                if (i >= coin) {

                    final String additionalChange = "-" + coin;
                    final int leftOverChange = i - coin;

                    for (final String currentChange : changes.get(leftOverChange)) {
                        final String totalChange = currentChange + additionalChange;
                        final String sortedTotalChange = Arrays.stream(totalChange.split("-"))
                                .mapToInt(Integer::valueOf)
                                .sorted()
                                .mapToObj(it -> Integer.toString(it))
                                .collect(Collectors.joining("-"));

                        changes.get(i).add(sortedTotalChange);
                    }
                }
            }
        }

        //System.out.println(changes);

        return changes.get(change).size();
    }
}
