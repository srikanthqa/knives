package com.github.knives.java.bloomfilter;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.junit.Test;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.PrimitiveSink;

public class BloomFilterTest {

	@Test
	public void test() {
		final SecureRandom random = new SecureRandom();
		BigInteger bigInteger = new BigInteger(8, random);
		
		//Creating the BloomFilter
		BloomFilter<byte[]> bloomFilter = BloomFilter.create(Funnels.byteArrayFunnel(), 1000);

		//Putting elements into the filter
		//A BigInteger representing a key of some sort
		bloomFilter.put(bigInteger.toByteArray());

		//Testing for element in set
		BigInteger bitIntegerII = new BigInteger(8, random);
		
		boolean mayBeContained = bloomFilter.mightContain(bitIntegerII.toByteArray());
		System.out.println(mayBeContained);
	}

	//Create the custom filter
	static class BigIntegerFunnel implements Funnel<BigInteger> {
        @Override
        public void funnel(BigInteger from, PrimitiveSink into) {
            into.putBytes(from.toByteArray());
        }
    }

	@Test
	public void test2() {
		final SecureRandom random = new SecureRandom();
		BigInteger bigInteger = new BigInteger(8, random);
		
		//Creating the BloomFilter
		BloomFilter<BigInteger> bloomFilter = BloomFilter.create(new BigIntegerFunnel(), 1000);

		//Putting elements into the filter
		//A BigInteger representing a key of some sort
		bloomFilter.put(bigInteger);

		//Testing for element in set
		BigInteger bitIntegerII = new BigInteger(8, random);
		
		//Testing for element in set
		boolean mayBeContained = bloomFilter.mightContain(bitIntegerII);
		
		System.out.println(mayBeContained);
	}
}
