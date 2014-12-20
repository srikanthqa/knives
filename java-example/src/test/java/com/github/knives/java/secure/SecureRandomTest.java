package com.github.knives.java.secure;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.junit.Test;

public class SecureRandomTest {

	@Test
	public void testOneRandomByte() {
		final SecureRandom random = new SecureRandom();
		//uniformly distributed over the range 0 to (2^numBits - 1)
		System.out.println("0x" + new BigInteger(8, random).toString(16));
	}
	
	@Test
	public void testOneRandomWord() {
		final SecureRandom random = new SecureRandom();
		System.out.println("0x" + new BigInteger(16, random).toString(16));
	}

	@Test
	public void testOneRandomDWord() {
		final SecureRandom random = new SecureRandom();
		System.out.println("0x" + new BigInteger(32, random).toString(16));
	}

}
