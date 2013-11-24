@Grapes([
	@Grab(group='org.apache.camel', module='camel-core', version='2.12.1'),
	@Grab(group='org.slf4j', module='slf4j-simple', version='1.7.5')
])

import org.apache.camel.CamelContext
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.builder.RouteBuilder

final def cli = new CliBuilder(usage: 'CamelFileCopier -f /path/to/source -t /path/to/dest')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.t( longOpt: 'to', argName: 't', required: true, args: 1, 'destination' )
cli.f( longOpt: 'from', argName: 'f', required: true, args: 1, 'source')
//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def source = opt.f
final def destination = opt.t

CamelContext context = new DefaultCamelContext()
context.addRoutes(new RouteBuilder() {
	public void configure() {
		from("file:${source}?noop=true").to("file:${destination}")
	}
})

context.start()

// non-determine time of copy, but after all this is just an
// example of camel
sleep(10000)

context.stop()


			