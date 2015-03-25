package com.github.knives.dojo.problem;

import java.util.Arrays;

/**
 * Chief's bot is playing an old DOS-based game. There are N+1 buildings in the game - indexed from 0 to N and are placed left-to-right.
 * It is guaranteed that building with index 0 will be of height 0 unit. For buildings with index i (i∈[1,N]) height will be hi units.
 *
 * At beginning Chief's bot is at building with index 0. At each step, bot jumps to next (right) building.
 * Suppose bot is at kth building and his current energy is botEnergy, then in next step he will jump to (k+1)th building.
 * He will gain/lose energy equal in amount to difference between hk+1 and botEnergy
 *
 * If hk+1 > botEnergy, then he will lose hk+1−botEnergy units of energy.
 * Otherwise, he will gain botEnergy−hk+1 units of energy.
 *
 * Goal is to reach Nth building, and during the course bot should never have negative energy units.
 * What should be the minimum units of energy with which bot should start to successfully complete the game?
 */
public interface ChiefHopper {

    public static int minimumInitialEnergy(int[] buildings) {
        /**
         * If e(n-1) < h(n), then e(n) = e(n-1) - (h(n) - e(n-1)) = 2*e(n-1) - h(n)
         * Else e(n) = e(n-1) + (e(n-1) - h(n)) = 2*e(n-1) - h(n)
         *
         * Therefore, e(n) = 2*e(n-1) - h(n)
         * e(n-1) = ceil ((e(n) + h(n)) / 2)
         */

        int minE = 0;

        for (int i = buildings.length - 1; i >= 0 ; i--) {
            minE = (minE + buildings[i] + 1) / 2;
        }

        return minE;
    }
}
