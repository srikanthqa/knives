package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedJohnIsBackTest {
    @Test
    public void test() {
        //assertEquals(1, RedJohnIsBack.computeArrangement(1));
        //assertEquals(5, RedJohnIsBack.computeArrangement(7));
        //assertEquals(7, RedJohnIsBack.computeArrangement(8));
        //assertEquals(10, RedJohnIsBack.computeArrangement(9));
        //assertEquals(138588, RedJohnIsBack.computeArrangement(40));


        /**
         34
         3
         31
         35
         10
         38
         18
         27
         15
         3
         38
         14
         18
         4
         5
         23
         9
         31
         10
         25
         */

        /*
        3385
        0
        1432
        4522
        6
        10794
        42
        462
        19
        0
        10794
        15
        42
        1
        2
        155
        4
        1432
        6
        269
         */

        //assertEquals(0, RedJohnIsBack.compute(1));
        //assertEquals(3, RedJohnIsBack.compute(7));
        System.out.println(RedJohnIsBack.choose(22, 4));
        System.out.println(RedJohnIsBack.computeArrangement(12));
        assertEquals(3385, RedJohnIsBack.compute(34));
        //assertEquals(12895, RedJohnIsBack.compute(40));
    }
}
