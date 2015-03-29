package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * Your algorithms have become so good at predicting the market that you now
 * know what the share price of Wooden Orange Toothpicks Inc. (WOT) will be for the next N days.
 *
 * Each day, you can either buy one share of WOT,
 * sell any number of shares of WOT that you own,
 * or not make any transaction at all.
 * What is the maximum profit you can obtain with an optimum trading strategy?
 *
 * Input
 *
 * The first line contains the number of test cases T. T test cases follow:
 *
 * The first line of each test case contains a number N. The next line contains N integers,
 * denoting the predicted price of WOT shares for the next N days.
 *
 * Output
 *
 * Output T lines, containing the maximum profit which can be obtained for the corresponding test case.
 *
 * Constraints
 *
 * 1 <= T <= 10
 * 1 <= N <= 50000
 *
 * All share prices are between 1 and 100000
 */
public interface StockMaximize {
    public static long computeProfit(int[] prices) {

        // the optimal stragety is to sell all, because
        // supposedly, the only reason is you don't sell all (i.e. sell less than all) at price[i] on day i
        // because in future day, you can make a higher profit by selling at price[j] on day j
        // Is it better to sell all at price[j] on day j to make a better and higher profit

        long[] profits = new long[prices.length];

        // profits[i] = max( profits[j] + sum_{j <= k < i} max(prices[i] - prices[k], 0) ) for 0 <= j < i
        for (int i = 1; i < prices.length; i++) {

            for (int j = i-1; j >= 0; j--) {

                // go backward if hit higher or equal price
                // that mean stop and add the profits on that day
                if (prices[j] >= prices[i]) {
                    profits[i] += profits[j];
                    break;
                }

                profits[i] +=  prices[i] - prices[j];
            }

            /*
            for (int j = 0; j < i; j++) {
                long profitEarn = 0;

                for (int k = j; k < i; k++) {
                    profitEarn += Math.max(prices[i] - prices[k], 0);
                }

                profits[i] = Math.max(profits[i], profits[j] + profitEarn);
            }
            */
        }

        System.out.println(Arrays.toString(profits));

        return profits[prices.length - 1];
    }
}
