package com.github.knives.dojo.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


public interface Combination {
	/**
	 * http://www.growingwiththeweb.com/2013/06/algorithm-all-combinations-of-set.html
	 * 
	 * Recursively generate combination
	 * d row = d
	 * c row = c+{}, c+d
	 *       = c, cd
	 * b row = b+{}, b+d, b+c, b+cd
	 *       = b, bd, bc, bcd
	 * c row = a+{}, a+d, a+c, a+cd, a+b, a+bc, a+bd, a+bcd
	 *       = a, ad, ac, acd, ab, abc, abd, abcd
	 */
	static List<String> generate(String text) {
		final ArrayList<String> results = new ArrayList<String>();
	    for (int i = 0; i < text.length(); i++) {
	        // Record size as we will be adding to the list
	    	// neat trick
	        int resultsLength = results.size();
	        for (int j = 0; j < resultsLength; j++) {
	            results.add(text.charAt(i) + results.get(j));
	        }
	        // generate {} + text[i] set
	        results.add(Character.toString(text.charAt(i)));
	    }
	    return results;
	}
	
	/**
	 * Given an array of size n, return all combination of 2
	 * elements of the array
	 * 
	 * @return List of indices pair
	 * 
	 * The key to generate is nested loop
	 * inner loop start index is equal to outer loop current 
	 * 
	 * This is in order generation
	 */
	static List<int[]> choose2(int n) {
		final List<int[]> combination = new ArrayList<int[]>();
		for (int i = 0; i < n-2+1; i++) {
			for (int j = i+1; j < n; j++) {
				combination.add(new int[] {i, j});
			}
		}
		return combination;
	}
	
	/**
	 * Given an array of size n, return all combination of k
	 * elements of the array
	 * 
	 * @return List of k-tuples indices 
	 * 
	 * The idea is same as choose2, but since number of loop is dynamic i.e. k
	 * 
	 * we maintain an indicies array i.e. set below
	 * where set[0] = outermost loop indices
	 * 
	 * when we call next(set, n, k), it will generate the next in-order
	 * set using same logic where inner loop start index is equal to outer loop current 
	 * i.e. set[i+1] = set[i]+1 
	 */
    static List<int[]> choose(final int n, final int k) {
        final List<int[]> sets = new ArrayList<int[]>();
        final int[] set = IntStream.range(0, k).toArray();

        do {
            sets.add(Arrays.copyOf(set, k));
        } while (next(set, n, k));

        return sets;
    }

    static boolean next(int[] set, int n, int k) {
    	// start with the innermost loop index
        int i = k - 1; 
        while(i > 0) {
            if (set[i] == n-1) {
                set[i] = 0;
                i--;
            } else {
                set[i]++;
                for (; i < k-1; i++) {
                    set[i+1] = set[i]+1;
                }
                return true;
            }
        }

        if (set[i] == n-k) {
            return false;
        }

        set[i]++;
        for (; i < k-1; i++) {
            set[i+1] = set[i]+1;
        }

        return true;
    }
}
