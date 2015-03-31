package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RedJohnIsBackTest {
    final private RedJohnIsBack john = new RedJohnIsBack();

    @Test
    public void test() {
        /*
        System.out.print("new long[] {");
        for (int i = 1; i <= 40; i++) {
            System.out.print(john.computeArrangement(i));
            System.out.print(", ");
        }
        System.out.println("};");
        */

        //assertEquals(1, john.computeArrangement(1));
        //assertEquals(5, john.computeArrangement(7));
        //assertEquals(7, john.computeArrangement(8));
        //assertEquals(10, john.computeArrangement(9));
        //assertEquals(138588, john.computeArrangement(40));


        assertEquals(0, john.compute(1));
        assertEquals(3, john.compute(7));
        assertEquals(3385, john.compute(34));
        assertEquals(19385, john.compute(40));
    }
}
