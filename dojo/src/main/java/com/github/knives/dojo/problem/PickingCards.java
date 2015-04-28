package com.github.knives.dojo.problem;

public interface PickingCards {
	static long modulo = 1000000007L;
	
	static long count(int[] cards) {
		final int N = cards.length;
		
		// counting sort
		final long[] buckets = new long[N+1];
		for (int i = 0; i < N; i++) {
			buckets[cards[i]]++;
		}
		
		
		long countWay = 1;
		long cardInHand = 0;
		long cardOnHold = 0;
		
		for (int i = 0; i <= N; i++) {
			if (buckets[i] > 0) {
				final long cardToPickup = i - cardInHand;
				final long totalCardCanBePickUp = cardOnHold;
				if (cardToPickup > totalCardCanBePickUp) {
					return 0;
				}
				
				// compute new card to put on hold
				cardOnHold = totalCardCanBePickUp - cardToPickup  + buckets[i];
				
				// obviously you need to have in hand at least i value
				cardInHand = i;
				
				
				countWay *= factorialWithModulo(totalCardCanBePickUp, cardToPickup);
				countWay %= modulo;
				
				System.out.println(totalCardCanBePickUp + " " + cardToPickup + " " + cardOnHold + " " + cardInHand + " " + countWay);
			}
		}
		
		// then put everything to hand
		countWay *= factorialWithModulo(cardOnHold, cardOnHold);
		countWay %= modulo;

		System.out.println(cardOnHold + " " + cardInHand + " " + countWay);

		return countWay;
	}
	
	static long factorialWithModulo(long n, long k) {
		long product = 1;
		while (n > 1 && k > 0) {
			product *= n;
			product %= modulo;
			n--;
			k--;
		}
		
		return product;
	}
}
