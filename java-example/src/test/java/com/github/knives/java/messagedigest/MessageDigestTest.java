package com.github.knives.java.messagedigest;

import static org.junit.Assert.*;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class MessageDigestTest {
	
	private static String digest(MessageDigest md, byte[] bytes) {
		final StringBuffer writer = new StringBuffer ();
		md.update(bytes);
		
		byte[] digest = md.digest();
		
		for (byte b : digest) {
			writer.append(String.format("%02X", b));
		}
		
		return writer.toString();
	}
	
	public static String sha1(byte[] bytes) throws NoSuchAlgorithmException {
		return digest(MessageDigest.getInstance("SHA-1"), bytes);
	}
	
	public static String md5(byte[] bytes) throws NoSuchAlgorithmException {
		return digest(MessageDigest.getInstance("MD5"), bytes);
	}
	
	@Test
	public void test() throws NoSuchAlgorithmException {
		assertEquals("cbb11ed87dc8a95d81400c7f33c7c171", 
				md5("echo".getBytes(StandardCharsets.US_ASCII)).toLowerCase());
		
		assertEquals("b2d21e771d9f86865c5eff193663574dd1796c8f",
				sha1("echo".getBytes(StandardCharsets.US_ASCII)).toLowerCase());
	}

}
