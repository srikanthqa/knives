package com.github.knives.dojo.problem;

/**
 * 5:00  five o' clock
 * 5:01  one minute past five
 * 5:10  ten minutes past five
 * 5:30  half past five
 * 5:40  twenty minutes to six
 * 5:45  quarter to six
 * 5:47  thirteen minutes to six
 * 5:28  twenty eight minutes past five
 */
public interface TheTimeInWords {
	static String convert(int H, int M) {
		String direction = "past";
		if (M > 30) {
			H = H == 12 ? 1: H+1;
			direction = "to";
		}
		
		String hour = string(H);
		if (M == 0) {
			return hour + " " + "o' clock";
		}
		
		String minute, minuteUnit;
		if (M > 30) {
			minute = string(60 - M);
			minuteUnit = (60 - M) == 1 ? "minute" : (60 - M == 15) ? null : "minutes";
		} else {
			minute = string(M);
			minuteUnit = M == 1 ? "minute" : (M == 30 || M == 15) ? null : "minutes";
		}

		StringBuilder builder = new StringBuilder();
		builder.append(minute).append(" ");
		
		if (minuteUnit != null) {
			builder.append(minuteUnit).append(" ");
		}
		
		builder.append(direction).append(" ").append(hour);
		
		return builder.toString();
	}
	
	static String string(int val) {
		switch (val) {
		case 1: return "one";
		case 2: return "two";
		case 3: return "three";
		case 4: return "four";
		case 5: return "five";
		case 6: return "six";
		case 7: return "seven";
		case 8: return "eight";
		case 9: return "nine";
		case 10: return "ten";
		case 11: return "eleven";
		case 12: return "twelve";
		case 13: return "thirteen";
		case 14: return "fourteen";
		case 15: return "quarter";
		case 16: return "sixteen";
		case 17: return "seventeen";
		case 18: return "eighteen";
		case 19: return "nineteen";
		case 20: return "twenty";
		case 21: 
		case 22:
		case 23:
		case 24: 
		case 25: 
		case 26: 
		case 27: 
		case 28: 
		case 29: return "twenty " + string(val - 20);
		case 30: return "half";
		default: throw new IllegalArgumentException("unable to convert value " + val);
		}
	}
}
