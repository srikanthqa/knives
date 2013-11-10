/*
 * RepackJar repacks an existing jar with new class files (replaced the old one)
 * 
 * @TODO: add support for same class name but different package
 */

 @Grapes([
	 @Grab(group='com.google.guava', module='guava', version='15.0')
 ])
 
import com.google.common.io.Files

final def cli = new CliBuilder(usage: 'RepackJar <class files/directory>')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.j( longOpt: 'jar', argName: 'j', required: true, args: 1, 'jar files to repack with new class files' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def classFiles = opt.arguments()
final def originalJarFile = opt.j
final def originalJarFileWithoutExt = originalJarFile.substring(0, originalJarFile.size() - '.jar'.size())
final def tempDir = Files.createTempDir()
final def unpackJarDir = tempDir.canonicalPath + '/' +  originalJarFileWithoutExt
final def repackedJarFile = tempDir.canonicalPath + '/' + originalJarFile

final def ant = new AntBuilder()

ant.unjar( src: originalJarFile, dest: unpackJarDir )

final def copyToDir(ant, dir, oldFile, newFile) {
	dir.eachFileMatch(oldFile) { originalFile ->
		ant.echo("Replace ${originalFile} with ${newFile}")
		ant.copy(file: newFile, tofile: originalFile)
	}
}

final def unpackJarDirFile = new File(unpackJarDir)

classFiles.each { classFile ->
	def newClassFile = new File(classFile)
	if (newClassFile.exists() && newClassFile.canRead()) {
		copyToDir(ant, unpackJarDirFile, classFile, newClassFile)
		
		unpackJarDirFile.eachDirRecurse { subdir ->
			copyToDir(ant, subdir, classFile, newClassFile)
		}
	}
}


ant.jar( destfile: repackedJarFile) {
	fileset( dir: unpackJarDir )
}

ant.echo("Cleaning up")
ant.delete(includeemptydirs: true) {
	fileset( dir: unpackJarDir )
}

ant.echo("The repacked jar located at ${repackedJarFile}")
