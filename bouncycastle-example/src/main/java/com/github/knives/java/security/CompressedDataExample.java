package com.github.knives.java.security;

import java.util.Arrays;

/**
 * Basic use of CMS compressed-data.
 */
public class CompressedDataExample {
	public static void main(String args[]) throws Exception {
		// set up the generator
		CMSCompressedDataGenerator gen = new CMSCompressedDataGenerator();

		// compress the data
		CMSProcessableByteArray data = new CMSProcessableByteArray(
				"Hello world!".getBytes());

		CMSCompressedData compressed = gen.generate(data,
				CMSCompressedDataGenerator.ZLIB);

		// recreate and uncompress the data
		compressed = new CMSCompressedData(compressed.getEncoded());

		byte[] recData = compressed.getContent();

		// compare uncompressed data to the original data
		if (Arrays.equals((byte[]) data.getContent(), recData)) {
			System.out.println("data recovery succeeded");
		} else {
			System.out.println("data recovery failed");
		}
	}
}
