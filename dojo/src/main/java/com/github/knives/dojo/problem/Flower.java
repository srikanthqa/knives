package com.github.knives.dojo.problem;

import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

/*
 * You and your K-1 friends want to buy N flowers. Flower number i has cost c(i).
 *
 * Unfortunately the seller does not want just one customer to buy a lot of flowers, so
 * he tries to change the price of flowers for customers who have already bought some flowers.
 * More precisely, if a customer has already bought x flowers, he should pay (x+1)*c(i) dollars to buy flower number i.
 *
 * You and your K-1 friends want to buy all N flowers in such a way that you spend the least amount of money.
 * You can buy the flowers in any order.
 *
 * Input(k, costs)
 * Output: total cost
 */
public interface Flower {
    // The group rotately buy the flowers in order of highest cost to lowest cost
    // when anyone hold less flower should buy the next highest cost flower. Otherwise, the payment is higher.
    public static long totalCost(int k, List<Long> costs) {
        costs.sort(((Comparator<Long>) (Comparator.naturalOrder())).reversed());

        long totalCost = 0;
        int i = 0;
        ListIterator<Long> costIterator = costs.listIterator();
        while (costIterator.hasNext()) {
            final long cost = costIterator.next();
            final long x = i / k;
            totalCost += (x + 1) * cost;
            i++;
        }

        return totalCost;
    }
}
