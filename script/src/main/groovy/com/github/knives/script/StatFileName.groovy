package com.github.knives.script
import org.apache.commons.io.FilenameUtils

// http://commons.apache.org/proper/commons-io/apidocs/org/apache/commons/io/FilenameUtils.html

final def cli = new CliBuilder(usage: 'GitClone -u <url> -d <path/to/where/to/you/want>')
cli.h( longOpt: 'help', required: false, 'show usage information' )
//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage()
	return
}

if (opt.arguments().size() <= 0) {
	cli.usage()
}

opt.arguments().each { final filename ->
	use(FilenameUtils) {
		println filename.getFullPath()
		println filename.getFullPathNoEndSeparator()
		println filename.getName()
		println filename.getBaseName()
		println filename.getExtension()
		println filename.removeExtension()
	}
}
