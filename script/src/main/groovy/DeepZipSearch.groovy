@Grapes([
	@Grab(group='commons-io', module='commons-io', version='2.4')
])

import org.apache.commons.io.FilenameUtils


public class DeepZipSearch {
	final private static def ZIP_EXTENSIONS = ['zip', 'jar', 'war', 'ear']
	final private static def CLASS_EXTENSION = 'class'
	
	final def paths
	final def name
	
	public DeepZipSearch(def paths, def name) {
		this.paths = paths
		this.name = name
	}
	
	public void printSearchDetails() {
		paths.each { handleRawPath(it) }
	}
	
	private void handleRawPath(String path) {
		final def pathFile = new File(path)
		if (pathFile.exists()) {
			if (pathFile.isDirectory()) {
				handleFileDirectory(pathFile)
			} else {
				handleRegularFile(pathFile)
			}
		} else {
			println "$path does not exists"
		}
	}
	
	private void handleFileDirectory(final File directory) {
		
	}
	
	private void handleRegularFile(final File regularFile) {
		final def canonicalPath = regularFile.canonicalPath
		final def extension = FilenameUtils.getExtension(canonicalPath)
		final def baseName = FilenameUtils.getBaseName(canonicalPath)
		
		if (extension in ZIP_EXTENSIONS) {
			handleZipFile(regularFile)
		} else if (baseName == name) {
			if (extension == CLASS_EXTENSION) {
				printClassDetail(regularFile)
			} else {
				printNonClassDetail(regularFile)
			}
		}
	}
	
	private void handleZipFile(final File zipFile) {
		
	}
	
	private void printClassDetail(final File classFile) {
		
	}
	
	private void printNonClassDetail(final File nonClassFile) {
		println nonClassFile.canonicalPath
		
	}
	
	
	public static class JarClassLoader extends ClassLoader {
		
	}
	
	public static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'DeepZipSearch -n <name> [path/to/jars|directory|file]')
		cli.h( longOpt: 'help', required: false, 'show usage information' )
		cli.n( longOpt: 'name', args: 1, required: true, 'search class by name')
		
		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h) {
			cli.usage();
			return
		}
		
		def name = opt.n
		def paths = opt.arguments()
		new DeepZipSearch(paths, name).printSearchDetails()
	}
}