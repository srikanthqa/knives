package com.github.knives.dojo.algorithm;

public interface BinarySearch {
    static int lookup(int[] array, int value) {
        int low = 0;
        int high = array.length-1;

        while (low <= high) {
            //int mid = (high - low) / 2  + low;
            int mid = (low + high) / 2;

            System.out.println("low " + low + " high " + high + " mid " + mid);

            if (array[mid] == value) {
                return mid;
            } else if (array[mid] > value) {
                high = mid-1;
            } else {
                low = mid+1;
            }
        }

        return -1;
    }
}
