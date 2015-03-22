package com.github.knives.java.bitset;

import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.assertEquals;

public class BitSetTest {
    @Test
    public void testBitSet() {
        BitSet bitSet = new BitSet();
        bitSet.set(16);
        assertEquals(1L << 16, bitSet.toLongArray()[0]);
    }

}
