package com.github.knives.dojo.problem;



/**
 * Given an array with n elements, can you sort this array in ascending order using only one of the following operations?
 * 
 * Swap two elements.
 * Reverse one sub-segment.
 *
 * Output
 * 1. If the array is already sorted, output yes on the first line. You do not need to output anything else.
 *
 * If you can sort this array using one single operation (from the two permitted operations) then output yes on the first line and then:
 *
 * a. If you can sort the array by swapping dl and dr, output swap l r in the second line. l and r are the indices of the elements to be swapped, assuming that the array is indexed from 1 to n.
 * 
 * b. Else if it is possible to sort the array by reversing the segment d[l...r], output reverse l r in the second line. l and r are the indices of the first and last elements of the subsequence to be reversed, assuming that the array is indexed from 1 to n.
 * 
 * d[l...r] represents the sub-sequence of the array, beginning at index l and ending at index r, both inclusive.
 *
 * If an array can be sorted by either swapping or reversing, stick to the swap-based method.
 * 
 * If you cannot sort the array in either of the above ways, output no in the first line.
 */
public interface AlmostSorted {
	static interface Answer { }
	
	static Answer sort(int[] array) {
		final int N = array.length;
		
		// using slope array
		final boolean[] slope = new boolean[N];
		for (int i = 1; i < N; i++) {
			slope[i] = array[i-1] < array[i]; 
		}
		
		//System.out.println(Arrays.toString(slope));
		
		// then use a count for decrease slope
		int countDecreaseSlope = 0;
		for (int i = 1; i < N; i++) {
			if (slope[i] == false) {
				countDecreaseSlope++;
			}
		}
		
		if (countDecreaseSlope == 0) {
			// already sorted
			return Yes.SIR;
		} else if (countDecreaseSlope == 1) {
			// l,r are next to the each other
			for (int i = 1; i < N; i++) {
				if (slope[i] == false) {
					final int l = i-1;
					final int r = i;
					
					if ((l - 1 == -1 || array[l-1] < array[r]) &&
					   (r + 1 == N || array[l] < array[r+1])) {
						return new Swap(l, r);
					} else {
						return No.DICE;
					}
				}
			}
			
			return No.DICE;
		} else if (countDecreaseSlope == 2) {
			// banana split
			int l, r;
			for (l = 1; l < N; l++) {
				if (slope[l] == false) break;
			}
			
			for (r = l+1; r < N; r++) {
				if (slope[r] == false) break;
			}		
			
			//System.out.println("2 slope: l " + l + " r " + r);
			
			// check split assumption
			if (l+1 == r) {
				l -= 1;
				
				if ((l - 1 == -1 || array[l-1] < array[r]) &&
				   (r + 1 == N || array[l] < array[r+1])) {
					return new Reverse(l, r);
				} else {
					return No.DICE;
				}
			} else {
				l -= 1;

				if ((l - 1 == -1 || array[l-1] < array[r]) &&
				   (r + 1 == N || array[l] < array[r+1])) {
					return new Swap(l, r);
				} else {
					return No.DICE;
				}
			}
		} else {
			int l, r;
			for (l = 1; l < N; l++) {
				if (slope[l] == false) break;
			}
			
			for (r = l+1; r < N; r++) {
				if (slope[r] == true) break;
			}		
			
			for (int i = r+1; i < N; i++) {
				if (slope[i] == false) {
					return No.DICE;
				}
			}
			
			l -= 1;
			r -= 1;
			
			//System.out.println("2 slope: l " + l + " r " + r);
			
			if ((l - 1 == -1 || array[l-1] < array[r]) &&
			   (r + 1 == N || array[l] < array[r+1])) {
				return new Reverse(l, r);
			} else {
				return No.DICE;
			}
		}
	}
	
	static enum Yes implements Answer {
		SIR;
		public String toString() {
			return "yes\n";
		}
	}
	
	static enum No implements Answer {
		DICE;
		
		public String toString() {
			return "no\n";
		}
	}
	
	static class Swap implements Answer {
		private final int l, r;
		
		public Swap(int l, int r) {
			this.l = l+1;
			this.r = r+1;
		}
		
		public String toString() {
			return "yes\nswap " + l + " " + r + "\n";
		}
	}
	
	static class Reverse implements Answer {
		private final int l, r;
		
		public Reverse(int l, int r) {
			this.l = l+1;
			this.r = r+1;
		}
		
		public String toString() {
			return "yes\nreverse " + l + " " + r + "\n";
		}
	}
}
