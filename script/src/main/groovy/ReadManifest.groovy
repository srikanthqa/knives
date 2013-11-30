@Grapes([
	@Grab(group='org.apache.httpcomponents', module='fluent-hc', version='4.3.1')
])

import groovy.transform.Field
import java.io.File
import java.util.jar.JarFile
import java.util.jar.Manifest
import java.net.URL
import org.apache.http.client.fluent.Request
import org.apache.ivy.core.report.ResolveReport
import org.apache.ivy.core.module.id.ModuleRevisionId
import org.apache.ivy.Ivy
import org.apache.ivy.core.resolve.ResolveOptions

final def cli = new CliBuilder(usage: 'ReadManifest <path/to/file|url/to/file>')
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

def isUrl(identifier) {
	try {
		new URL(identifier)
		return true
	} catch (e) {
		return false
	}
}

def downloadJarFile(identifier) {
	final File temp = File.createTempFile("temp-jar-file-", ".jar")
	Request.Get(identifier).execute().saveContent(temp)
	
	return temp
}

opt.arguments().each { 
	final File file 
	
	if (isFile(it)) {
		file = new File(it)
	} else if (isUrl(it)) {
		file = downloadJarFile(it)
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