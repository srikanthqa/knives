import java.util.jar.JarFile
import java.util.jar.Manifest

final def cli = new CliBuilder(usage: 'ReadManifest')
cli.h( longOpt: 'help', required: false, 'show usage information' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

def isFile(identifier) {
	def test = new File(identifier)
	
	test.exists() && test.isFile() && ! test.isDirectory() && ! test.isHidden()
}

opt.arguments().each { 
	final File file 
	
	if (isFile(it)) {
		file = new File(it)
	} else {
		println "$it is not a valid identifier"
		return
	}
	
	try {
		final JarFile jarFile = new JarFile(file)
		final Manifest manifest = jarFile.getManifest()
		if (manifest) {
			manifest.getMainAttributes().each { attr ->
				println attr
			}
		} else {
			println "Unable to find manifest file"	
		}
		 
	} catch(IOException e) {
		println "Error openning $it due to $e"
		return
	}
}