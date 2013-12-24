package com.github.knives.script

import java.security.MessageDigest

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
	final def md5 = MessageDigest.getInstance("MD5")
	final def file = new File(it)
	final def writer = new OutputStreamWriter(System.out)
	
	md5.update(file.getBytes())
	md5.digest().encodeHex().writeTo(writer)
	
	writer.flush()
}