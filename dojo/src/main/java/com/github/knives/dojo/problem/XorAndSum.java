package com.github.knives.dojo.problem;

import java.math.BigInteger;

/**
 * You are given two positive integers a and b in binary representation. You should find the following sum modulo 10^9+7:
 *
 * sum(from i = 0 to 314159)  (a xor (b shl i))
 * where operation xor means exclusive OR operation, operation shl means binary shift to the left.
 *
 * Please note, that we consider ideal model of binary integers. That is there is infinite number of bits in each number,
 * and there are no disappearings (or cyclic shifts) of bits.
 */

public interface XorAndSum {

    public static BigInteger calculate(BigInteger a, BigInteger b) {
        // TODO: improve this!
        BigInteger sum = BigInteger.ZERO;
        BigInteger modulo = BigInteger.TEN.pow(9).add(new BigInteger("7"));
        for (int i = 0; i <= 314159; i++) {
            sum = sum.add(a.xor(b));
            b = b.shiftLeft(1);
        }

        return sum.mod(modulo);
    }
}
