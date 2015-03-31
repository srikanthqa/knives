package com.github.knives.dojo.problem;

import org.junit.Test;

public class SpecialPythagoreanTripletTest {
    @Test
    public void test() {
        System.out.println("long[] results = new long[3001];");
        System.out.println("Arrays.fill(results, -1);");
        for (int i = 1; i <= 3000; i++) {
            long result = SpecialPythagoreanTriplet.compute(i);
            if (result != -1) {
                System.out.println("results[" + i + "] = " + result + ";");
            }
        }
    }
}
