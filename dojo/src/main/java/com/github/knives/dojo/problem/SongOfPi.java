package com.github.knives.dojo.problem;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SongOfPi {
    public static void main(String[] args) throws Exception {
        final String PI_DIGITS = "31415926535897932384626433833";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		final int T = Integer.parseInt(in.readLine());
        
        for (int i = 0; i < T; i++) {
            final String line = in.readLine();
            final String[] words = line.split(" ");
            boolean isPiSong = true;
            for (int j = 0; j < words.length; j++) {
                if (words[j].length() != PI_DIGITS.charAt(j) - '0') {
                    isPiSong = false;
                    break;
                }
            }
            
            if (isPiSong) {
                System.out.println("It's a pi song.");
            } else {
                System.out.println("It's not a pi song.");
            }
        }
    }
}
