package com.github.knives.dojo.algorithm;

import java.util.ArrayList;
import java.util.List;

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
public interface Combination {
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
}
