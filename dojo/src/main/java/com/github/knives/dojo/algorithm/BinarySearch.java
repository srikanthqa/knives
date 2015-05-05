package com.github.knives.dojo.algorithm;

public interface BinarySearch {
	static int lookup(int[] array, int value) {
		if (array.length == 0) return -1;
		
		int low = 0;
		int high = array.length;
		// the array is empty, this condition go through
		while (low <= high) {
			// int mid = (high - low) / 2 + low;
			int mid = (low + high) / 2;

			System.out.println("low " + low + " high " + high + " mid " + mid);

			if (array[mid] == value) {
				return mid;
			} else if (array[mid] > value) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}

		return -1;
	}

	// fewer comparison lookup
	static int fcLookup(int[] array, int value) {
		if (array.length == 0) return -1;

		int low = 0;
		int high = array.length;
		
		// Invariant: A[l] <= key and A[r] > key
		// Boundary: |r - l| = 1
		// Input: A[l .... r-1]
		
		// this boundary because low <= mid < high
		while (high - low > 1) {
			int mid = (low + high) / 2;

			if (array[mid] <= value) {
				low = mid;
			} else {
				high = mid;
			}
		}

		// the array is empty, this will throw index out of bound
		if (array[low] == value)
			return low;
		else
			return -1;
	}
	
	// Invariant: A[l] <= value and A[r] > value
	// Boundary: |r - l| = 1
	// Input: A[l .... r-1]
	// Output: key for largest value <= given value
	// Precondition: A[l] <= key <= A[r]
	static int floor(int[] array, int value) {
		if (array.length == 0) return -1;

		int low = 0;
		int high = array.length;
		
	    while( high - low > 1 ) {
			int mid = (low + high) / 2;
	 
	        if( array[mid] <= value )
	            low = mid;
	        else
	            high = mid;
	    }
	 
	    return low;
	}
	
	// Input: Indices Range (l ... r]
	// Invariant: A[r] >= value and A[l] > value
	// Output: key for smallest value >= given value
	static int ceiling(int[] array, int value) {
		if (array.length == 0) return -1;

		int low = 0;
		int high = array.length;
		
	    while( high - low > 1 ) {
			int mid = (low + high) / 2;
	 
	        if( array[mid] >= value )
	            high = mid;
	        else
	            low = mid;
	    }
	 
	    return high;
	}
	
	static int indexOfMinElementInRotatedArray(int[] array) {
		if (array.length == 0) return 0;

		// extreme condition, size zero or size two
	    int low = 0;
	    int high = array.length - 1;
	    
	    // Precondition: A[low] > A[high]
	    if( array[low] <= array[high] )
	        return low;
	 
	    while( low <= high ) {
	        // Termination condition (low will eventually falls on high, and high always
	        // point minimum possible value)
	        if( low == high )
	            return low;
	 
	        // looking for pulse
			int mid = (low + high) / 2;
	 
	        if( array[mid] < array[high] )
	            // min can't be in the range (mid < min <= high)
	            // we can exclude A[mid+1 ... high]
	            high = mid;
	        else
	            // min must be in the range (mid < min <= high),
	            // we must search in A[m+1 ... r]
	            low = mid+1;
	    }
	 
	    return -1;
	}
	
	// return the first element of the value
	static int dupLookup(int[] array, int value) {
		if (array.length == 0) return -1;
		
		int low = 0;
		int high = array.length;
		
		while (high - low > 1) {
			int mid = (low + high)/2;
			
			// first element can't be in array[low...mid]
			if (array[mid] < value) {
				low = mid+1;
			} else {
				high = mid;
			}
		}
		
		return high;
	}
}
