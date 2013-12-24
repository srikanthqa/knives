package com.github.knives.script

import java.security.MessageDigest

class Hash {
	
	public static String md5(byte[] bytes) {
		final def md5 = MessageDigest.getInstance("MD5")
		final def writer = new StringWriter()
		
		md5.update(bytes)
		md5.digest().encodeHex().writeTo(writer)
		
		return writer.toString()
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
