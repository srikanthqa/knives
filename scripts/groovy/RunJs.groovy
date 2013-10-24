/*
 * RunJs is an example to run javascript from
 * commandline using rhino javascript engine
 */

@Grapes([
	@Grab(group='rhino', module='js', version='1.7R4')
])

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