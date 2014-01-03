package com.github.knives.script

import java.security.MessageDigest

class Hash {
	
	private static String digest(MessageDigest md, byte[] bytes) {
		final def writer = new StringWriter()
		md.update(bytes)
		md.digest().encodeHex().writeTo(writer)
		return writer.toString()
	}
	
	public static String sha1(byte[] bytes) {
		return digest(MessageDigest.getInstance("SHA-1"), bytes)
	}
	
	public static String md5(byte[] bytes) {
		return digest(MessageDigest.getInstance("MD5"), bytes)
	}
	
	public static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'MD5 [path/to/file]')
		cli.h( longOpt: 'help', required: false, 'show usage information' )
		
		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h) {
			cli.usage();
			return
		}
		
		opt.arguments().each {
			final def file = new File(it)
			println md5(file.getBytes())
		}
	}
}
