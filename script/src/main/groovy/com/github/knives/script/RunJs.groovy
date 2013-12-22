package com.github.knives.script
/*
 * RunJs is an example to run javascript from
 * commandline using rhino javascript engine
 */

final def cli = new CliBuilder(usage: 'TrimJar <jar> <classpath>')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.j( longOpt: 'js', argName: 'j', required: true, args: 1, 'javascript to run' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def jsFileName = opt.j