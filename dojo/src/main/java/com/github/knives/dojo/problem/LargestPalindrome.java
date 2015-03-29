package com.github.knives.dojo.problem;

/**
 * A palindromic number reads the same both ways. The smallest 6 digit palindrome made from the product of two 3-digit numbers is
 * 101101=143×707.
 *
 Find the largest palindrome made from the product of two 3-digit numbers which is less than N.

 Input Format
 First line contains T that denotes the number of test cases. This is followed by T lines, each containing an integer, N.

 Output Format
 Print the required answer for each test case in a new line.

 Constraints
 1≤T≤100
 101101<N<1000000
 */
public interface LargestPalindrome {
    public static void compute() {
        for (int i = 100; i < 1000; i++) {
            for (int j = 100; j <= i; j++) {
                final int product = i * j;
                if (isPalindrome(product)) {
                    //System.out.println(i + " " + j);
                    System.out.println("cache.add(" + product + ");");
                }
            }
        }
    }

    public static boolean isPalindrome(int value) {
        final String s = Integer.toString(value);
        for (int i = 0; i < s.length() / 2; i++) {
            if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
                return false;
            }
        }

        return true;
    }
}
