package com.github.knives.functionaljava;

import fj.data.Array;
import org.junit.Test;
import static fj.data.Array.array;
import static fj.data.List.fromString;
import static fj.function.Characters.isLowerCase;
import static fj.Show.arrayShow;
import static fj.Show.intShow;
import static fj.function.Integers.even;


public class ArrayTest {
    @Test
    public void testArrayExist() {
        final Array<String> a = array("Hello", "There", "what", "DAY", "iS", "iT");
        final boolean b = a.exists(s -> fromString(s).forall(isLowerCase));
        System.out.println(b); // true ("what" is the only value that qualifies; try removing it)
    }

    @Test
    public void testArrayFilter() {
        final Array<Integer> a = array(97, 44, 67, 3, 22, 90, 1, 77, 98, 1078, 6, 64, 6, 79, 42);
        final Array<Integer> b = a.filter(even);
        final Array<Integer> c = a.filter(i -> i % 2 == 0);
        arrayShow(intShow).println(b); // {44,22,90,98,1078,6,64,6,42}
    }
}
