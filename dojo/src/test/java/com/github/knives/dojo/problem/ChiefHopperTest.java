package com.github.knives.dojo.problem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChiefHopperTest {
    @Test
    public void test() {
        assertEquals(0, ChiefHopper.minimumInitialEnergy(new int[]{}));

        assertEquals(1, ChiefHopper.minimumInitialEnergy(new int[]{2}));

        assertEquals(4, ChiefHopper.minimumInitialEnergy(new int[]{3, 4, 3, 2, 4}));

        assertEquals(4, ChiefHopper.minimumInitialEnergy(new int[]{4, 4, 4}));

        assertEquals(3, ChiefHopper.minimumInitialEnergy(new int[]{4, 4}));
    }
}
