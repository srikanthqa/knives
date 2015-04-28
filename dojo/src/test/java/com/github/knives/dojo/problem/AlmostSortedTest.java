package com.github.knives.dojo.problem;

import static org.junit.Assert.assertEquals;

import java.util.Scanner;

import org.junit.Test;

public class AlmostSortedTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(ClassLoader.getSystemResourceAsStream("almostsorted/input_1.txt"));
        final int N = scanner.nextInt();
        final int[] array = new int[N];
        for (int i = 0; i < N; i++) {
            array[i] = scanner.nextInt();
        }
        
        System.out.print(AlmostSorted.sort(array).toString());
    }
    
	@Test
	public void test() {
		
		main(new String[] {});
		
		String answer;
		
		answer = AlmostSorted.sort(new int[] {3, 1, 2}).toString();
		assertEquals("no\n", answer);
		
		answer = AlmostSorted.sort(new int[] {4, 2}).toString();
		assertEquals("yes\nswap 1 2\n", answer);
		
		answer = AlmostSorted.sort(new int[] {5, 4, 3}).toString();
		assertEquals("yes\nreverse 1 3\n", answer);

		answer = AlmostSorted.sort(new int[] {1, 5, 4, 3, 2, 6}).toString();
		assertEquals("yes\nreverse 2 5\n", answer);
		
	

		
	}
}
