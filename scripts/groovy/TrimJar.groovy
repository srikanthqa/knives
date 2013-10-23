/*
 * TrimJar uses ProGuard to shrink fat jar to have smaller footprint
 * 
 *  ProGuard: http://proguard.sourceforge.net/
 */
            
@Grapes([
	@GrabConfig(systemClassLoader=true),
	@Grab(group='net.sf.proguard', module='proguard-base', version='4.10'),
	@Grab(group='net.sf.proguard', module='proguard-anttask', version='4.10')
]) 

final def cli = new CliBuilder(usage: 'TrimJar <jar> <classpath>')
cli.h( longOpt: 'help', required: false, 'show usage information' )
//cli.j( longOpt: 'jar', argName: 'j', required: true, args: 1, 'jar files to repack with new class files' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def ant = new AntBuilder()

ant.taskdef(name: 'proguard', classname: 'proguard.ant.ProGuardTask')
ant.taskdef(name: 'proguardconfiguration', classname: 'proguard.ant.ConfigurationTask')
